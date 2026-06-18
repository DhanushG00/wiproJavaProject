package com.wipro.order_service.service;



import java.util.List;

import com.wipro.order_service.dto.CartRequestDTO;
import com.wipro.order_service.dto.CartResponseDTO;

public interface CartService {

    CartResponseDTO addToCart(CartRequestDTO request);

    List<CartResponseDTO> getCartByUser(Long userId);

    CartResponseDTO updateCart(Long id, CartRequestDTO request);

    void deleteCartItem(Long id);

    void clearCart(Long userId);
}