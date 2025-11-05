package com.ecommerce.cart_service.dto;

public record ProductDetails(
    String productId,
    double price,
    Byte[] productImage,
    String productName,
    Boolean available
) {}