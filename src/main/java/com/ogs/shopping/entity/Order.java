package com.ogs.shopping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private double totalAmount;
    private double discountAmount;
    private double payableAmount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
