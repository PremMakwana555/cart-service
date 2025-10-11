package com.ecommerce.cart_service.service;

import com.ecommerce.cart_service.dto.CartItemRequest;
import com.ecommerce.cart_service.dto.CartRequest;
import com.ecommerce.cart_service.dto.CartResponse;

public interface CartService {
    CartResponse getCart(String userId);

    // Existing method (can be deprecated later if unused by controllers)
    CartResponse addToCart(CartRequest request);

    // New endpoints aligned with CartController
    CartResponse addItemToCart(String userId, CartItemRequest itemRequest);

    CartResponse updateCartItem(String userId, String productId, CartItemRequest itemRequest);

    CartResponse removeItem(String userId, String productId);

    void clearCart(String userId);

    void checkout(String userId);
}
