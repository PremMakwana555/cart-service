package com.ecommerce.cart_service.dto;

import java.math.BigDecimal;

public record CartItemResponse(
                String productId,
                int quantity,
                BigDecimal price,
                String imageUrl,
                String productName,
                Boolean available) {
}
