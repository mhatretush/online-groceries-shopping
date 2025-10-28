package com.ogs.shopping.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private double productPrice;

    @Column(nullable = false)
    private int productQty;

    @ManyToOne()
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;
}// Product class ends
