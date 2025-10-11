package com.ecommerce.cart_service.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CartResponse {
    private String id;
    private String userId;
    private List<CartItemResponse> items;
    private Double totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // getters and setters
}

class CartItemResponse {
    private String productId;
    private int quantity;
    private double price;
    private Byte[] productImage;
    private String productName;
    private Boolean available;
    // getters and setters
}
