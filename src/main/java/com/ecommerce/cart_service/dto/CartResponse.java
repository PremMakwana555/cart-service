package com.ecommerce.cart_service.dto;

import java.util.List;

public class CartResponse {
    private String id;
    private String userId;
    private List<CartItemResponse> items;
    // getters and setters
}

class CartItemResponse {
    private String productId;
    private int quantity;
    // getters and setters
}
