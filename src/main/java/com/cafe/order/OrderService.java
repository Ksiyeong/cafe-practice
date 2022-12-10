package com.cafe.order;

import com.cafe.coffee.CoffeeService;
import com.cafe.member.MemberService;
import org.springframework.stereotype.Service;

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
        return orderRepository.findById(orderId).get();
    }

    public void verifyOrder(Order order) {
        // 유효한 멤버인지 확인
        memberService.findVerifiedMember(order.getMember().getMemberId());

        // 유효한 커피인지 확인
        order.getOrderCoffees().stream()
                .forEach(orderCoffee -> coffeeService.findVerifiedCoffee(orderCoffee.getCoffee().getCoffeeId()));
    }
}
