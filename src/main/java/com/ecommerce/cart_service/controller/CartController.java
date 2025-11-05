package com.ecommerce.cart_service.controller;

import com.ecommerce.cart_service.dto.CartItemRequest;
import com.ecommerce.cart_service.dto.CartResponse;
import com.ecommerce.cart_service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/cart")
@Validated
public class  CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public CartResponse getCart(@PathVariable String userId) {
        return cartService.getCart(userId);
    }

    @PostMapping("/items")
    public CartResponse addItemToCart(Principal principal, @RequestBody CartItemRequest cartItemRequest) {
        if (principal == null || principal.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing user principal");
        }
        String userId = principal.getName();
        return cartService.addItemToCart(userId, cartItemRequest);
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
