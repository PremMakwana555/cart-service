package com.ecommerce.cart_service.service;

import com.ecommerce.cart_service.dto.CartRequest;
import com.ecommerce.cart_service.dto.CartResponse;

public interface CartService {
    CartResponse getCart(String userId);
    CartResponse addToCart(CartRequest request);
    CartResponse removeItem(String userId, String productId);
    void clearCart(String userId);
}
