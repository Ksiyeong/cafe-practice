package com.cafe.order;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        //Todo: 아이디 확인 및 기타 로직 구현

        return orderRepository.save(order);
    }
}
