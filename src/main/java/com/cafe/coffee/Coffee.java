package com.cafe.coffee;

import com.cafe.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Coffee extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coffeeId;

    @Column(length = 100, nullable = false, unique = true)
    private String korName;

    @Column(length = 100, nullable = false, unique = true)
    private String engName;

    @Column(length = 5, nullable = false)
    private Integer price;
}

