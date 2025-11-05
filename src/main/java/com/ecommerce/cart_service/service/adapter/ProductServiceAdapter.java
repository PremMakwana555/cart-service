package com.ecommerce.cart_service.service.adapter;

import com.ecommerce.cart_service.client.ProductFeignClient;
import com.ecommerce.cart_service.dto.ProductDetails;
import com.ecommerce.cart_service.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceAdapter implements ProductService {

    private final ProductFeignClient feign;

    public ProductServiceAdapter(ProductFeignClient feign) {
        this.feign = feign;
    }

    @Override
    public ProductDetails fetchProduct(String productId) {
        // add retries/caching/fallbacks here if needed
        return feign.getProductById(productId);
    }
}