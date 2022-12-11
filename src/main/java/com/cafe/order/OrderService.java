package com.cafe.order;

import com.cafe.coffee.CoffeeService;
import com.cafe.exception.BusinessLogicException;
import com.cafe.exception.ExceptionCode;
import com.cafe.member.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final CoffeeService coffeeService;

    public OrderService(OrderRepository orderRepository, MemberService memberService, CoffeeService coffeeService) {
        this.orderRepository = orderRepository;
        this.memberService = memberService;
        this.coffeeService = coffeeService;
    }

    public Order createOrder(Order order) {
        verifyOrder(order);

        // 스탬프 찍기
        calStamp(order, true);

        return orderRepository.save(order);
    }

    public Order updateOrder(Order order) {
        Order findOrder = findVerifiedOrder(order.getOrderId());

        Optional.ofNullable(order.getOrderStatus())
                .ifPresent(orderStatus -> findOrder.setOrderStatus(orderStatus));

        return orderRepository.save(findOrder);
    }

    public Order findOrder(long orderId) {
        return findVerifiedOrder(orderId);
    }

    public Page<Order> findOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(
                page, size, Sort.by("orderId").descending()
        ));
    }

    public void cancelOrder(long orderId) {
        Order order = findVerifiedOrder(orderId);

        // 주문 확정 시 취소 불가능
        if (order.getOrderStatus().getStepNumber() >= 2) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_ORDER);
        }

        // 주문 상태를 취소로 변경
        order.setOrderStatus(Order.OrderStatus.ORDER_CANCEL);

        // 스탬프 카운트 롤백
        calStamp(order, false);

        orderRepository.save(order);
    }

    private Order findVerifiedOrder(long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order findOrder = optionalOrder.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
        return findOrder;
    }

    private void verifyOrder(Order order) {
        // 유효한 멤버인지 확인
        memberService.findVerifiedMember(order.getMember().getMemberId());

        // 유효한 커피인지 확인
        order.getOrderCoffees().stream()
                .forEach(orderCoffee -> coffeeService.findVerifiedCoffee(orderCoffee.getCoffee().getCoffeeId()));
    }

    private void calStamp(Order order, boolean positive) {
        // true 를 주면 스탬프를 증가시키고 false 를 주면 스탬프를 감소시킨다
        int num = -1;
        if (positive) num = 1;
        memberService.findMember(order.getMember().getMemberId()).getStamp().setStampCount(num * getStampCount(order));
    }

    private int getStampCount(Order order) { // 해당 주문에 있는 커피 주문량을 계산해준다
        return order.getOrderCoffees().stream()
                .map(orderCoffee -> orderCoffee.getQuantity())
                .mapToInt(quantity -> quantity)
                .sum();
    }
}
