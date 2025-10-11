package com.ecommerce.cart_service.controller;

import com.ecommerce.cart_service.dto.CartItemRequest;
import com.ecommerce.cart_service.dto.CartResponse;
import com.ecommerce.cart_service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public CartResponse getCart(@PathVariable String userId) {
        return cartService.getCart(userId);
    }

    @PostMapping("/{userId}/items")
    public CartResponse addItemToCart(@PathVariable String userId, @RequestBody CartItemRequest itemRequest) {
        return cartService.addItemToCart(userId, itemRequest);
    }

    @PutMapping("/{userId}/items/{productId}")
    public CartResponse updateCartItem(@PathVariable String userId, @PathVariable String productId, @RequestBody CartItemRequest itemRequest) {
        return cartService.updateCartItem(userId, productId, itemRequest);
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public CartResponse removeItem(@PathVariable String userId, @PathVariable String productId) {
        return cartService.removeItem(userId, productId);
    }

    @DeleteMapping("/{userId}")
    public void clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
    }

    @PostMapping("/{userId}/checkout")
    public void checkout(@PathVariable String userId) {
        cartService.checkout(userId);
    }
}
