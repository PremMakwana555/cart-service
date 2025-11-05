package com.ecommerce.cart_service.service;

import com.ecommerce.cart_service.dto.ProductDetails;

public interface ProductService {
    ProductDetails fetchProduct(String productId);
}