package com.ecommerce.cart_service.dto;

public record CartItemResponse(
        String productId,
        int quantity,
        double price,
        String imageUrl,
        String productName,
        Boolean available) {
}
