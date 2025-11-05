package com.ecommerce.cart_service.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CartRequest(
    String cartId,
    String userId,
    List<CartItemRequest> items,
    Double totalAmount,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
