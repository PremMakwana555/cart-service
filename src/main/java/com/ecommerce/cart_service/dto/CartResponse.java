package com.ecommerce.cart_service.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CartResponse(
    String id,
    String userId,
    List<CartItemResponse> items,
    Double totalAmount,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}

