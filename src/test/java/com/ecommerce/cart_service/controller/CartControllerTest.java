package com.ecommerce.cart_service.controller;

import com.ecommerce.cart_service.dto.CartItemRequest;
import com.ecommerce.cart_service.dto.CartResponse;
import com.ecommerce.cart_service.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user1")
    void getCart_Success() throws Exception {
        when(cartService.getCart("user1")).thenReturn(new CartResponse("cart1", "user1", null, 0.0, null, null));

        mockMvc.perform(get("/cart/{userId}", "user1"))
                .andExpect(status().isOk());

        verify(cartService).getCart("user1");
    }

    @Test
    @WithMockUser(username = "user2")
    void getCart_Forbidden_WhenAccessingOtherUserCart() throws Exception {
        mockMvc.perform(get("/cart/{userId}", "user1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void getCart_Unauthorized_WhenNoUser() throws Exception {
        mockMvc.perform(get("/cart/{userId}", "user1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user1")
    void addItemToCart_Success() throws Exception {
        CartItemRequest request = new CartItemRequest("prod1", 1);
        when(cartService.addItemToCart(eq("user1"), any(CartItemRequest.class)))
                .thenReturn(new CartResponse("cart1", "user1", null, 0.0, null, null));

        mockMvc.perform(post("/cart/items")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(cartService).addItemToCart(eq("user1"), any(CartItemRequest.class));
    }

    @Test
    @WithMockUser(username = "user1")
    void updateCartItem_Success() throws Exception {
        CartItemRequest request = new CartItemRequest("prod1", 2);

        mockMvc.perform(put("/cart/{userId}/items/{productId}", "user1", "prod1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(cartService).updateCartItem(eq("user1"), eq("prod1"), any(CartItemRequest.class));
    }

    @Test
    @WithMockUser(username = "user2")
    void updateCartItem_Forbidden() throws Exception {
        CartItemRequest request = new CartItemRequest("prod1", 2);

        mockMvc.perform(put("/cart/{userId}/items/{productId}", "user1", "prod1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user1")
    void removeItem_Success() throws Exception {
        mockMvc.perform(delete("/cart/{userId}/items/{productId}", "user1", "prod1")
                .with(csrf()))
                .andExpect(status().isOk());

        verify(cartService).removeItem("user1", "prod1");
    }

    @Test
    @WithMockUser(username = "user1")
    void clearCart_Success() throws Exception {
        mockMvc.perform(delete("/cart/{userId}", "user1")
                .with(csrf()))
                .andExpect(status().isOk());

        verify(cartService).clearCart("user1");
    }
}
