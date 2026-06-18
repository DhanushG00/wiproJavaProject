package com.wipro.order_service.controller;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.order_service.dto.CartRequestDTO;
import com.wipro.order_service.dto.CartResponseDTO;
import com.wipro.order_service.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public CartResponseDTO addToCart(@Valid @RequestBody CartRequestDTO request) {
        return cartService.addToCart(request);
    }

    @GetMapping("/{userId}")
    public List<CartResponseDTO> getCart(@PathVariable Long userId) {
        return cartService.getCartByUser(userId);
    }

    @PutMapping("/{id}")
    public CartResponseDTO updateCart(@PathVariable Long id,
                                      @Valid @RequestBody CartRequestDTO request) {
        return cartService.updateCart(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteCart(@PathVariable Long id) {
        cartService.deleteCartItem(id);
        return "Cart item deleted successfully";
    }

    @DeleteMapping("/clear/{userId}")
    public String clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return "Cart cleared successfully";
    }
}