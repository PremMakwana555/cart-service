package com.ecommerce.cart_service.mapper;

import com.ecommerce.cart_service.dto.CartItemRequest;
import com.ecommerce.cart_service.dto.CartItemResponse;
import com.ecommerce.cart_service.model.Cart;
import com.ecommerce.cart_service.dto.CartResponse;
import com.ecommerce.cart_service.dto.CartRequest;
import com.ecommerce.cart_service.model.CartItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {
    public static CartResponse toResponse(Cart cart) {
        // map Cart to CartResponse
        List<CartItemResponse> cartItems = cart.getItems().stream().map(cartItem -> {
            return new CartItemResponse(
                cartItem.getProductId(),
                cartItem.getQuantity(),
                cartItem.getPrice(),
                cartItem.getProductImage(),
                cartItem.getProductName(),
                cartItem.getAvailable()
            );
        }).toList();
        CartResponse response = new CartResponse(
            cart.getId(),
            cart.getUserId(),
            cartItems, // Assuming items are already in the correct format
            cart.getTotalAmount(),
            cart.getCreatedAt(),
            cart.getUpdatedAt()
        );
        return response;
    }
    public static Cart toEntity(CartRequest cartRequest) {
        // map CartRequest to Cart
        Cart cart = new Cart();
        cart.setUserId(cartRequest.userId());
        cart.setTotalAmount(0.0);
        cart.setItems(new java.util.ArrayList<>());
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        // Note: Mapping items would require additional logic
        return null;
    }
}
