package com.ecommerce.cart_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private String productId;
    private int quantity;
    private double price;
    private String imageUrl;
    private String productName;
    private Boolean available;
    // getters and setters
}
