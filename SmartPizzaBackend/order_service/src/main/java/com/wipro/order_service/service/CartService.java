package com.wipro.order_service.service;

import java.util.List;
import com.wipro.order_service.dto.CartDTO;

public interface CartService {

	CartDTO addToCart(Long userId,Long menuID,int quantity);

	List<CartDTO> getCartByUser(Long userId);

	CartDTO updateCart(Long id, int quantity);

	void deleteCartItem(Long cartId);

	void clearCart(Long userId);
}