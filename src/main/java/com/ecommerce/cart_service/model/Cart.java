package com.ecommerce.cart_service.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "carts")
public class Cart {
    @Id
    private String id;
    private String userId;
    private List<CartItem> items;
    private java.math.BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @org.springframework.data.annotation.Version
    private Long version;

    // getters and setters

    public Cart(String userId) {
        this.userId = userId;
        this.items = items != null ? items : new java.util.ArrayList<>();
        this.totalAmount = java.math.BigDecimal.ZERO;
        this.createdAt = java.time.LocalDateTime.now();
        this.updatedAt = java.time.LocalDateTime.now();
        this.id = null;
    }
}