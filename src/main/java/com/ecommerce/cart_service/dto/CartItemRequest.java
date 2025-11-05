package com.ecommerce.cart_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CartItemRequest(

    @NotBlank
    String productId,

    @Min(1)
    int quantity
) {}
