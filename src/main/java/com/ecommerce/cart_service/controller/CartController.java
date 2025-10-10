package com.ecommerce.cart_service.controller;

import com.ecommerce.cart_service.dto.CartRequest;
import com.ecommerce.cart_service.dto.CartResponse;
import com.ecommerce.cart_service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
package com.ecommerce.cart_service.model;
@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public CartResponse getCart(@PathVariable String userId) {
        return cartService.getCart(userId);
    }

    @PostMapping
    public CartResponse addToCart(@RequestBody CartRequest request) {
        return cartService.addToCart(request);
    }

    @DeleteMapping("/{userId}/item/{productId}")
    public CartResponse removeItem(@PathVariable String userId, @PathVariable String productId) {
        return cartService.removeItem(userId, productId);
    }

    @DeleteMapping("/{userId}")
    public void clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
    }
}

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "carts")
public class Cart {
    @Id
    private String id;
    private String userId;
    private List<CartItem> items;
    // getters and setters
}
