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
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public CartResponse getCart(@PathVariable String userId, Principal principal) {
        validatePrincipal(principal, userId);
        return cartService.getCart(userId);
    }

    @PostMapping("/items")
    public CartResponse addItemToCart(Principal principal,
            @RequestBody @jakarta.validation.Valid CartItemRequest cartItemRequest) {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        return cartService.addItemToCart(principal.getName(), cartItemRequest);
    }

    @PutMapping("/{userId}/items/{productId}")
    public CartResponse updateCartItem(@PathVariable String userId, @PathVariable String productId,
            @RequestBody @jakarta.validation.Valid CartItemRequest itemRequest, Principal principal) {
        validatePrincipal(principal, userId);
        return cartService.updateCartItem(userId, productId, itemRequest);
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public CartResponse removeItem(@PathVariable String userId, @PathVariable String productId, Principal principal) {
        validatePrincipal(principal, userId);
        return cartService.removeItem(userId, productId);
    }

    @DeleteMapping("/{userId}")
    public void clearCart(@PathVariable String userId, Principal principal) {
        validatePrincipal(principal, userId);
        cartService.clearCart(userId);
    }

    @PostMapping("/{userId}/checkout")
    public void checkout(@PathVariable String userId, Principal principal) {
        validatePrincipal(principal, userId);
        cartService.checkout(userId);
    }

    private void validatePrincipal(Principal principal, String userId) {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        if (!principal.getName().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied to other user's cart");
        }
    }
}
