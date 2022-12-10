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
        order.getOrderCoffees().stream()
                .forEach(orderCoffee ->
                        memberService.findMember(order.getMember().getMemberId()).getStamp()
                                .setStampCount(orderCoffee.getQuantity()));

        return orderRepository.save(order);
    }

    public Order findOrder(long orderId) {
        return findVerifiedOrder(orderId);
    }

    public Page<Order> findOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(
                page, size, Sort.by("orderId").descending()
        ));
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
}
