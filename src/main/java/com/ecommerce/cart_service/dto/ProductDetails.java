package com.ecommerce.cart_service.dto;

public record ProductDetails(
                Long id,
                java.math.BigDecimal price,
                String imageUrl,
                String name,
                Boolean available) {
}