package com.ecommerce.cart_service.service.impl;

import com.ecommerce.cart_service.dto.CartItemRequest;
import com.ecommerce.cart_service.dto.CartRequest;
import com.ecommerce.cart_service.dto.CartResponse;
import com.ecommerce.cart_service.dto.ProductDetails;
import com.ecommerce.cart_service.mapper.CartItemMapper;
import com.ecommerce.cart_service.mapper.CartMapper;
import com.ecommerce.cart_service.model.Cart;
import com.ecommerce.cart_service.model.CartItem;
import com.ecommerce.cart_service.repository.CartRepository;
import com.ecommerce.cart_service.service.CartService;
import com.ecommerce.cart_service.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        // Initialize any required dependencies here (e.g., repositories, Kafka producers)
    }

    @Override
    public CartResponse getCart(String userId) {
        // TODO: implement fetching cart by userId
        return null;
    }

    @Override
    public CartResponse addToCart(CartRequest request) {
        // TODO: implement add to cart using CartRequest (legacy)
        return null;
    }

    @Override
    public CartResponse addItemToCart(String userId, CartItemRequest cartItemRequest) {

        //fetch the user's cart if already exists else create a new cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> new Cart(userId));


        // Check if the item already exists in the cart
        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(cartItemRequest.productId()))
                .findFirst();

        // maintain a single itemPrice variable as primitive double
        double itemPrice;
        int quantityToAdd = cartItemRequest.quantity();

        if (existingItemOpt.isPresent()) {
            // If item exists, update the quantity by requested amount
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantityToAdd);

            // existingItem.getPrice() is a primitive double; use directly
            itemPrice = existingItem.getPrice();
        } else {
            // If item does not exist, fetch product once and add as a new item
            ProductDetails product = productService.fetchProduct(cartItemRequest.productId());

            // Use mapper to build CartItem from request + product details
            CartItem newItem = CartItemMapper.toEntity(cartItemRequest, product);

            cart.getItems().add(newItem);

            // set itemPrice from fetched product (or 0.0 if product missing)
            itemPrice = (product != null) ? product.price() : 0.0;
        }


        // Update the total amount (assume cart.getTotalAmount() and setTotalAmount use Double)
        double currentTotal = cart.getTotalAmount() != null ? cart.getTotalAmount() : 0.0;
        cart.setTotalAmount(currentTotal + itemPrice * quantityToAdd);

        //save the cart
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);

        return CartMapper.toResponse(cart);
    }

    @Override
    public CartResponse updateCartItem(String userId, String productId, CartItemRequest itemRequest) {
        // TODO: implement update cart item for userId and productId
        return null;
    }

    @Override
    public CartResponse removeItem(String userId, String productId) {
        // TODO: implement remove item from cart for userId and productId
        return null;
    }

    @Override
    public void clearCart(String userId) {
        // TODO: implement clear cart for userId
    }

    @Override
    public void checkout(String userId) {
        // TODO: implement checkout logic and publish event to Kafka
    }
}
