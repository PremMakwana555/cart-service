package com.ecommerce.cart_service.dto;

public record ProductDetails(
        String productId,
        double price,
        String imageUrl,
        String name,
        Boolean available) {
}