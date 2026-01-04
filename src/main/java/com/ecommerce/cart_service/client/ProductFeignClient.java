// src/main/java/com/ecommerce/cart_service/client/ProductFeignClient.java
package com.ecommerce.cart_service.client;

import com.ecommerce.cart_service.dto.ProductDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.cloud.openfeign.FeignClient;

// If product.service.url is unset, the url placeholder resolves to null and Feign uses the service name (Eureka)
@FeignClient(name = "${product.service.name}", url = "${product.service.url}", fallback = ProductFeignClientFallback.class)
public interface ProductFeignClient {
    @GetMapping("${product.service.api.products}/{id}")
    ProductDetails getProductById(@PathVariable("id") String id);
}
