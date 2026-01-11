package com.ecommerce.cart_service.mapper;

import com.ecommerce.cart_service.dto.CartItemRequest;
import com.ecommerce.cart_service.dto.CartItemResponse;
import com.ecommerce.cart_service.dto.ProductDetails;
import com.ecommerce.cart_service.model.CartItem;

public class CartItemMapper {
    public static CartItem toEntity(CartItemRequest request) {
        if (request == null)
            return null;
        CartItem item = new CartItem();
        item.setProductId(request.productId());
        item.setQuantity(request.quantity());
        return item;
    }

    // New mapper: combine request + product details into a CartItem
    public static CartItem toEntity(CartItemRequest request, ProductDetails product) {
        CartItem item = toEntity(request);
        if (item == null) {
            item = new CartItem();
        }
        if (product != null) {
            item.setPrice(product.price());
            item.setImageUrl(product.imageUrl());
            item.setProductName(product.name());
            // Default to true if null, as ProductService doesn't send this field
            item.setAvailable(product.available() != null ? product.available() : true);
            // ensure productId aligns with product if request omitted or different
            if (item.getProductId() == null || item.getProductId().isEmpty()) {
                item.setProductId(String.valueOf(product.id()));
            }
        }
        return item;
    }

    public static CartItemResponse toDto(CartItem item) {
        if (item == null)
            return null;
        return new CartItemResponse(
                item.getProductId(),
                item.getQuantity(),
                item.getPrice(),
                item.getImageUrl(),
                item.getProductName(),
                item.getAvailable());
    }
}
