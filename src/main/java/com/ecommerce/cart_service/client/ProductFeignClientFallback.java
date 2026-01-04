package com.ecommerce.cart_service.client;

import com.ecommerce.cart_service.dto.ProductDetails;
import org.springframework.stereotype.Component;

@Component
public class ProductFeignClientFallback implements ProductFeignClient {
    @Override
    public ProductDetails getProductById(String id) {
        // Fallback: return valid structure but with "unavailable" status
        return new ProductDetails(id, 0.0, null, "ServiceName Unavailable", false);
    }
}
