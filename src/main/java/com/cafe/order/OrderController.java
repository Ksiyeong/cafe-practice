package com.cafe.order;

import com.cafe.dto.MultiResponseDto;
import com.cafe.dto.SingleResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
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
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.orderToOrderResponseDto(order)),
                HttpStatus.CREATED);
    }

    // TODO: updateOrder 구현 -> 주문 상태 변경하는 기능 (주인장이)
    @PatchMapping("/{order-id}")
    public ResponseEntity patchOrder(@Positive @PathVariable("order-id") long orderId,
                                     @Valid @RequestBody OrderDto.Patch orderPatchDto) {
        orderPatchDto.setOrderId(orderId);
        Order order = orderService.updateOrder(mapper.orderPatchDtoToOrder(orderPatchDto));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.orderToOrderResponseDto(order)),
                HttpStatus.OK);
    }

    @GetMapping("/{order-id}")
    public ResponseEntity getOrder(@Positive @PathVariable("order-id") long orderId) {
        Order order = orderService.findOrder(orderId);
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.orderToOrderResponseDto(order)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getOrders(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<Order> pageOrders = orderService.findOrders(page - 1, size);

        return new ResponseEntity(
                new MultiResponseDto<>(mapper.ordersToOrderResponsesDto(pageOrders.getContent()), pageOrders),
                HttpStatus.OK);
    }

    //TODO: DeleteOrder 구현하기
    @DeleteMapping("/{order-id}")
    public ResponseEntity deleteOrder(@Positive @PathVariable("order-id") long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
