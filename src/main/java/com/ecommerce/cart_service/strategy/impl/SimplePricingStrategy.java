package com.ecommerce.cart_service.strategy.impl;

import com.ecommerce.cart_service.model.Cart;
import com.ecommerce.cart_service.strategy.PricingStrategy;
import org.springframework.stereotype.Component;

@Component
public class SimplePricingStrategy implements PricingStrategy {

    @Override
    public double calculateTotal(Cart cart) {
        if (cart == null || cart.getItems() == null) {
            return 0.0;
        }
        return cart.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}
