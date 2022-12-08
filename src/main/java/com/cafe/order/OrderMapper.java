package com.cafe.order;

import com.cafe.coffee.Coffee;
import org.mapstruct.Mapper;

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

    OrderDto.Response orderToOrderResponseDto(Order order);
}
