package com.cafe.coffee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

public class CoffeeDto {
    @Getter
    public static class Post {
        @NotBlank(message = "한글 커피 이름은 공백이 아니어야 합니다.")
        private String korName;

        @NotBlank
        @Pattern(regexp = "^([A-Za-z])(\\s?[A-Za-z])*$",
                message = "커피명(영문)은 영문이어야 합니다(단어 사이 공백 한 칸 포함). 예) Cafe Latte")
        private String engName;

        @Range(min = 100, max = 50000)
        private int price;
    }

    @Getter // mapper에서 get을 통해 해당객체에서 값을 추출해오는듯 없으면 null됨
    public static class Patch {
        @Setter
        private long coffeeId;

        private String korName;

        @Pattern(regexp = "^([A-Za-z])(\\s?[A-Za-z])*$",
                message = "커피명(영문)은 영문이어야 합니다(단어 사이 공백 한 칸 포함). 예) Cafe Latte")
        private String engName;

        @Range(min = 100, max = 50000)
        private Integer price;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private long coffeeId;
        private String korName;
        private String engName;
        private int price;
    }

}
