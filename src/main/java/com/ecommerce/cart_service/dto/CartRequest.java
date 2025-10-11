package com.ecommerce.cart_service.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CartRequest {
    private String cartId;
    private String userId;
    private List<CartItemRequest> items;
    private Double totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // getters and setters
}

