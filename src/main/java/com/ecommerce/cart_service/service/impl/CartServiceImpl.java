package com.ecommerce.cart_service.service.impl;

import com.ecommerce.cart_service.dto.CartRequest;
import com.ecommerce.cart_service.dto.CartResponse;
import com.ecommerce.cart_service.service.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Override
    public CartResponse getCart(String userId) {
        // stub
        return null;
    }
    @Override
    public CartResponse addToCart(CartRequest request) {
        // stub
        return null;
    }
    @Override
    public CartResponse removeItem(String userId, String productId) {
        // stub
        return null;
    }
    @Override
    public void clearCart(String userId) {
        // stub
    }
}
