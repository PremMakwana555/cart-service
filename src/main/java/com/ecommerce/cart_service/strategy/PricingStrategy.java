package com.ecommerce.cart_service.strategy;

import com.ecommerce.cart_service.model.Cart;

public interface PricingStrategy {
    java.math.BigDecimal calculateTotal(Cart cart);
}
