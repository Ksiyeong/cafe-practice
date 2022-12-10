package com.cafe.order;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OrderCoffeeDto {

    @Getter
    public static class Post {
        @Positive
        private long coffeeId;

        @Positive
        private int quantity;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private long coffeeId;
        private int quantity;
        private String korName;
        private String engName;
        private Integer price;
    }

}
