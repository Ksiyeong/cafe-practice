package com.cafe.order;

import com.cafe.coffee.Coffee;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    default Order orderPostDtoToOrder(OrderDto.Post orderPostDto) {
        Order order = new Order();
        order.setMember(orderPostDto.getMember());

        orderPostDto.getOrderCoffees().stream()
                .forEach(orderCoffeeDto -> {

                    OrderCoffee orderCoffee = new OrderCoffee();
                    orderCoffee.setOrder(order);

                    Coffee coffee = new Coffee();
                    coffee.setCoffeeId(orderCoffeeDto.getCoffeeId());
                    orderCoffee.setCoffee(coffee);

                    orderCoffee.setQuantity(orderCoffeeDto.getQuantity());

                    order.addOrderCoffees(orderCoffee);
                });

        return order;
    }

    default OrderDto.Response orderToOrderResponseDto(Order order) {
        OrderDto.Response orderResponseDto = new OrderDto.Response();

        orderResponseDto.setOrderId(order.getOrderId());
        orderResponseDto.setMemberId(order.getMember().getMemberId());
        orderResponseDto.setOrderStatus(order.getOrderStatus());
        orderResponseDto.setOrderCoffees(order.getOrderCoffees().stream()
                .map(orderCoffee -> {
                    OrderCoffeeDto.Response orderCoffeeResponseDto = new OrderCoffeeDto.Response();
                    orderCoffeeResponseDto.setCoffeeId(orderCoffee.getCoffee().getCoffeeId());
                    orderCoffeeResponseDto.setQuantity(orderCoffee.getQuantity());
                    orderCoffeeResponseDto.setKorName(orderCoffee.getCoffee().getKorName());
                    orderCoffeeResponseDto.setEngName(orderCoffee.getCoffee().getEngName());
                    orderCoffeeResponseDto.setPrice(orderCoffee.getCoffee().getPrice());
                    return orderCoffeeResponseDto;
                })
                .collect(Collectors.toList()));
        orderResponseDto.setCreatedAt(order.getCreatedAt());

        return orderResponseDto;
    }

    List<OrderDto.Response> ordersToOrderResponsesDto(List<Order> orders);

}
