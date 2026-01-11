package com.ecommerce.cart_service.client;

import com.ecommerce.cart_service.dto.ProductDetails;
import org.springframework.stereotype.Component;

@Component
public class ProductFeignClientFallback implements ProductFeignClient {
    @Override
    public ProductDetails getProductById(String id) {
        Long parsedId = null;
        try {
            parsedId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            // log error or keep null
        }
        return new ProductDetails(parsedId, java.math.BigDecimal.ZERO, null, "ServiceName Unavailable", false);
    }
}
