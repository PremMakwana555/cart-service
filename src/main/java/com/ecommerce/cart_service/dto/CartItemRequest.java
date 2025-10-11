package com.ecommerce.cart_service.dto;

public class CartItemRequest {
    private String productId;
    private int quantity;
    private double price;
    private Byte[] productImage; // Image as byte array
    private String productName;
    private Boolean available;
    // getters and setters
}
