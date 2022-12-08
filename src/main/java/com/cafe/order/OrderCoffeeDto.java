package com.cafe.order;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class OrderCoffeeDto {

    @Getter
    public static class Post {
        @Positive
        private long coffeeId;

        @Positive
        private int quantity;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private long coffeeId;
        private int quantity;
        private String korName;
        private String engName;
        private int price;
    }

}
