package com.ecommerce.cart_service.dto;

public record CartItemResponse(
    String productId,
    int quantity,
    double price,
    Byte[] productImage,
    String productName,
    Boolean available
) {}
