package com.ecommerce.cart_service.mapper;

import com.ecommerce.cart_service.dto.CartItemRequest;
import com.ecommerce.cart_service.dto.CartItemResponse;
import com.ecommerce.cart_service.dto.ProductDetails;
import com.ecommerce.cart_service.model.CartItem;

public class CartItemMapper {
    public static CartItem toEntity(CartItemRequest request) {
        if (request == null) return null;
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
            item.setProductImage(product.productImage());
            item.setProductName(product.productName());
            item.setAvailable(product.available());
            // ensure productId aligns with product if request omitted or different
            if (item.getProductId() == null || item.getProductId().isEmpty()) {
                item.setProductId(product.productId());
            }
        }
        return item;
    }

    public static CartItemResponse toDto(CartItem item) {
        if (item == null) return null;
        return new CartItemResponse(
            item.getProductId(),
            item.getQuantity(),
            item.getPrice(),
            item.getProductImage(),
            item.getProductName(),
            item.getAvailable()
        );
    }
}
