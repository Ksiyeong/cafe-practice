package com.cafe.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class MemberDto {
    @Getter
    @AllArgsConstructor // 테스트를 위해 추가됨
    public static class Post {
        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String name;

        @NotBlank
        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 10~11자리 숫자와 '-'로 구성되어야 합니다.")
        private String phone;
    }

    @Getter
    @Setter
    public static class Patch {
        private Long memberId;

        private String name;

        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 10~11자리 숫자와 '-'로 구성되어야 합니다.")
        private String phone;
    }


    @Getter
    @AllArgsConstructor
    public static class Response {
        private long memberId;
        private String email;
        private String name;
        private String phone;
    }

}
