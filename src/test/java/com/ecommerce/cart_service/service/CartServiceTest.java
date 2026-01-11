package com.ecommerce.cart_service.service;

import com.ecommerce.cart_service.dto.CartItemRequest;
import com.ecommerce.cart_service.dto.CartResponse;
import com.ecommerce.cart_service.dto.ProductDetails;
import com.ecommerce.cart_service.model.Cart;
import com.ecommerce.cart_service.model.CartItem;
import com.ecommerce.cart_service.repository.CartRepository;
import com.ecommerce.cart_service.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductService productService;

    @Mock
    private com.ecommerce.cart_service.strategy.PricingStrategy pricingStrategy;

    @InjectMocks
    private CartServiceImpl cartService;

    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart("user1");
        cart.setItems(new ArrayList<>());
        cart.setId("cart1");
    }

    @Test
    void getCart_CreatesNew_WhenNotFound() {
        when(cartRepository.findByUserId("user1")).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        CartResponse response = cartService.getCart("user1");

        assertNotNull(response);
        assertEquals("user1", response.userId());
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void addItemToCart_NewItem_Success() {
        when(cartRepository.findByUserId("user1")).thenReturn(Optional.of(cart));
        when(productService.fetchProduct("prod1"))
                .thenReturn(new ProductDetails(1L, java.math.BigDecimal.valueOf(100.0), null, "Product 1", true));
        when(pricingStrategy.calculateTotal(any(Cart.class))).thenReturn(java.math.BigDecimal.valueOf(200.0));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        CartItemRequest request = new CartItemRequest("prod1", 2);
        cartService.addItemToCart("user1", request);

        assertEquals(1, cart.getItems().size());
        verify(pricingStrategy).calculateTotal(any(Cart.class));
    }

    @Test
    void addItemToCart_ExistingItem_IncrementsQuantity() {
        CartItem item = new CartItem("prod1", 1, java.math.BigDecimal.valueOf(100.0), null, "Product 1", true);
        cart.getItems().add(item);
        cart.setTotalAmount(java.math.BigDecimal.valueOf(100.0));

        when(cartRepository.findByUserId("user1")).thenReturn(Optional.of(cart));
        when(pricingStrategy.calculateTotal(any(Cart.class))).thenReturn(java.math.BigDecimal.valueOf(300.0));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        CartItemRequest request = new CartItemRequest("prod1", 2);
        cartService.addItemToCart("user1", request);

        assertEquals(1, cart.getItems().size());
        assertEquals(3, cart.getItems().get(0).getQuantity());
        verify(pricingStrategy).calculateTotal(any(Cart.class));
    }

    @Test
    void updateCartItem_Success() {
        CartItem item = new CartItem("prod1", 1, java.math.BigDecimal.valueOf(100.0), null, "Product 1", true);
        cart.getItems().add(item);
        cart.setTotalAmount(java.math.BigDecimal.valueOf(100.0));

        when(cartRepository.findByUserId("user1")).thenReturn(Optional.of(cart));
        when(pricingStrategy.calculateTotal(any(Cart.class))).thenReturn(java.math.BigDecimal.valueOf(500.0));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        CartItemRequest request = new CartItemRequest("prod1", 5);
        cartService.updateCartItem("user1", "prod1", request);

        assertEquals(5, cart.getItems().get(0).getQuantity());
        verify(pricingStrategy).calculateTotal(any(Cart.class));
    }

    @Test
    void removeItem_Success() {
        CartItem item = new CartItem("prod1", 1, java.math.BigDecimal.valueOf(100.0), null, "Product 1", true);
        cart.getItems().add(item);
        cart.setTotalAmount(java.math.BigDecimal.valueOf(100.0));

        when(cartRepository.findByUserId("user1")).thenReturn(Optional.of(cart));
        when(pricingStrategy.calculateTotal(any(Cart.class))).thenReturn(java.math.BigDecimal.valueOf(0.0));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        cartService.removeItem("user1", "prod1");

        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    void clearCart_Success() {
        CartItem item = new CartItem("prod1", 1, java.math.BigDecimal.valueOf(100.0), null, "Product 1", true);
        cart.getItems().add(item);
        cart.setTotalAmount(java.math.BigDecimal.valueOf(100.0));

        when(cartRepository.findByUserId("user1")).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        cartService.clearCart("user1");

        assertTrue(cart.getItems().isEmpty());
        // No pricing strategy call expected for clearCart as it explicitly sets total
        // to 0.0
    }

    @Test
    void checkout_ClearsCart() {
        CartItem item = new CartItem("prod1", 1, java.math.BigDecimal.valueOf(100.0), null, "Product 1", true);
        cart.getItems().add(item);
        cart.setTotalAmount(java.math.BigDecimal.valueOf(100.0));

        when(cartRepository.findByUserId("user1")).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        cartService.checkout("user1");

        assertTrue(cart.getItems().isEmpty());
    }
}
