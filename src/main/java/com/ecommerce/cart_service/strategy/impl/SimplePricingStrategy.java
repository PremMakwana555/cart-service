package com.ecommerce.cart_service.strategy.impl;

import com.ecommerce.cart_service.model.Cart;
import com.ecommerce.cart_service.strategy.PricingStrategy;
import org.springframework.stereotype.Component;

@Component
public class SimplePricingStrategy implements PricingStrategy {

    @Override
    public java.math.BigDecimal calculateTotal(Cart cart) {
        if (cart == null || cart.getItems() == null) {
            return java.math.BigDecimal.ZERO;
        }
        return cart.getItems().stream()
                .map(item -> item.getPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity())))
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }
}
