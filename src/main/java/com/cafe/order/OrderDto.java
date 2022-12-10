package com.cafe.order;

import com.cafe.member.Member;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {
    @Getter
    public static class Post {
        @Positive
        private long memberId;

        @Valid
        private List<OrderCoffeeDto.Post> orderCoffees;

        public Member getMember() {
            Member member = new Member();
            member.setMemberId(memberId);
            return member;
        }

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private long orderId;
        private long memberId;
        private Order.OrderStatus orderStatus;

        private List<OrderCoffeeDto.Response> orderCoffees;

        @CreatedDate
        private LocalDateTime createdAt;
    }

}
