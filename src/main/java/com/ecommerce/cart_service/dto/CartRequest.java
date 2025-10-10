package com.ecommerce.cart_service.dto;

import java.util.List;

public class CartRequest {
    private String userId;
    private List<CartItemRequest> items;
    // getters and setters
}

class CartItemRequest {
    private String productId;
    private int quantity;
    // getters and setters
}
