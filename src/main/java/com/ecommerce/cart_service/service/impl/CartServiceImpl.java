package com.ecommerce.cart_service.service.impl;

import com.ecommerce.cart_service.dto.CartItemRequest;
import com.ecommerce.cart_service.dto.CartRequest;
import com.ecommerce.cart_service.dto.CartResponse;
import com.ecommerce.cart_service.service.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Override
    public CartResponse getCart(String userId) {
        // TODO: implement fetching cart by userId
        return null;
    }

    @Override
    public CartResponse addToCart(CartRequest request) {
        // TODO: implement add to cart using CartRequest (legacy)
        return null;
    }

    @Override
    public CartResponse addItemToCart(String userId, CartItemRequest itemRequest) {
        // TODO: implement add item to cart for userId
        return null;
    }

    @Override
    public CartResponse updateCartItem(String userId, String productId, CartItemRequest itemRequest) {
        // TODO: implement update cart item for userId and productId
        return null;
    }

    @Override
    public CartResponse removeItem(String userId, String productId) {
        // TODO: implement remove item from cart for userId and productId
        return null;
    }

    @Override
    public void clearCart(String userId) {
        // TODO: implement clear cart for userId
    }

    @Override
    public void checkout(String userId) {
        // TODO: implement checkout logic and publish event to Kafka
    }
}
