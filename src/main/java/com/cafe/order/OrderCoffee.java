package com.cafe.order;

import com.cafe.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class OrderCoffee extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderCoffeeId;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    @Column(nullable = false)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "COFFEE_ID")
    @Column(nullable = false)
    private Long coffeeId;

    private int quantity = 0;
}
