// src/main/java/com/ecommerce/cart_service/client/ProductFeignClient.java
package com.ecommerce.cart_service.client;

import com.ecommerce.cart_service.dto.ProductDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${product.service.name}", url = "${product.service.url:}")
public interface ProductFeignClient {
    @GetMapping("${product.service.api.products}/{id}")
    ProductDetails getProductById(@PathVariable("id") String id);
}
