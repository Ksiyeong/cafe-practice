package com.cafe.order;

import com.cafe.dto.SingleResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/orders")
@RestController
@Validated
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper mapper;

    public OrderController(OrderService orderService, OrderMapper mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postOrder(@Valid @RequestBody OrderDto.Post orderPostDto) {
        Order order = orderService.createOrder(mapper.orderPostDtoToOrder(orderPostDto));
        System.out.println("안녕");
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.orderToOrderResponseDto(order)),
                HttpStatus.CREATED);
    }

    @GetMapping("/{order-id}")
    public ResponseEntity getOrder(@Positive @PathVariable("order-id") long orderId) {
        Order order = orderService.findOrder(orderId);
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.orderToOrderResponseDto(order)), HttpStatus.OK);
    }

}
