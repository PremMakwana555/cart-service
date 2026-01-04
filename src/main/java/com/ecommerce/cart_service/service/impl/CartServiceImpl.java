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
    private final com.ecommerce.cart_service.strategy.PricingStrategy pricingStrategy;

    public CartServiceImpl(CartRepository cartRepository, ProductService productService,
            com.ecommerce.cart_service.strategy.PricingStrategy pricingStrategy) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.pricingStrategy = pricingStrategy;
    }

    @Override
    @org.springframework.cache.annotation.Cacheable(value = "cart", key = "#userId")
    public CartResponse getCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart(userId);
                    return cartRepository.save(newCart);
                });
        return CartMapper.toResponse(cart);
    }

    @Override
    public CartResponse addToCart(CartRequest request) {
        // Legacy method: delegate to addItemToCart using first item if available
        if (request.items() != null && !request.items().isEmpty()) {
            return addItemToCart(request.userId(), request.items().get(0));
        }
        return getCart(request.userId());
    }

    @Override
    @org.springframework.cache.annotation.CachePut(value = "cart", key = "#userId")
    public CartResponse addItemToCart(String userId, CartItemRequest cartItemRequest) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> new Cart(userId));

        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(cartItemRequest.productId()))
                .findFirst();

        int quantityToAdd = cartItemRequest.quantity();

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantityToAdd);
        } else {
            ProductDetails product = productService.fetchProduct(cartItemRequest.productId());
            CartItem newItem = CartItemMapper.toEntity(cartItemRequest, product);
            cart.getItems().add(newItem);
        }

        recalculateTotal(cart);
        cart.setUpdatedAt(LocalDateTime.now());
        Cart savedCart = cartRepository.save(cart);

        return CartMapper.toResponse(savedCart);
    }

    @Override
    @org.springframework.cache.annotation.CachePut(value = "cart", key = "#userId")
    public CartResponse updateCartItem(String userId, String productId, CartItemRequest itemRequest) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(
                        () -> new com.ecommerce.cart_service.exception.ResourceNotFoundException("Cart not found"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new com.ecommerce.cart_service.exception.ResourceNotFoundException(
                        "Item not found in cart"));

        item.setQuantity(itemRequest.quantity());

        recalculateTotal(cart);
        cart.setUpdatedAt(LocalDateTime.now());
        Cart savedCart = cartRepository.save(cart);

        return CartMapper.toResponse(savedCart);
    }

    @Override
    @org.springframework.cache.annotation.CachePut(value = "cart", key = "#userId")
    public CartResponse removeItem(String userId, String productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(
                        () -> new com.ecommerce.cart_service.exception.ResourceNotFoundException("Cart not found"));

        cart.getItems().removeIf(item -> item.getProductId().equals(productId));

        recalculateTotal(cart);
        cart.setUpdatedAt(LocalDateTime.now());
        Cart savedCart = cartRepository.save(cart);

        return CartMapper.toResponse(savedCart);
    }

    @Override
    @org.springframework.cache.annotation.CacheEvict(value = "cart", key = "#userId")
    public void clearCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(null);

        if (cart != null) {
            cart.getItems().clear();
            cart.setTotalAmount(0.0);
            cart.setUpdatedAt(LocalDateTime.now());
            cartRepository.save(cart);
        }
    }

    @Override
    @org.springframework.cache.annotation.CacheEvict(value = "cart", key = "#userId")
    public void checkout(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(
                        () -> new com.ecommerce.cart_service.exception.ResourceNotFoundException("Cart not found"));

        // In a real scenario, we would publish an OrderCreatedEvent here
        // For now, we simulate checkout by clearing the cart
        clearCart(userId);
    }

    private void recalculateTotal(Cart cart) {
        double total = pricingStrategy.calculateTotal(cart);
        cart.setTotalAmount(total);
    }
}
