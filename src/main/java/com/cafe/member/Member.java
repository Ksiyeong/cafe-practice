package com.cafe.member;

import com.cafe.audit.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
//@NoArgsConstructor
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Email
    @Column(length = 100, updatable = false, unique = true, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 13, nullable = false, unique = true)
    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$", message = "휴대폰 번호는 010으로 시작하는 10~11자리 숫자와 '-'로 구성되어야 합니다.")
    private String phone;
}
