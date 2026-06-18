package com.wipro.order_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.order_service.dto.CartDTO;
import com.wipro.order_service.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/add/{userId}/{menuID}/{quantity}")
	public CartDTO addToCart(@PathVariable Long userId, @PathVariable Long menuID, @PathVariable int quantity) {

		return cartService.addToCart(userId,menuID, quantity);
	}

	@GetMapping("/{userId}")
	public List<CartDTO> getCart(@PathVariable Long userId) {
		return cartService.getCartByUser(userId);
	}

	@PutMapping("/update/{id}/{quantity}")
	public CartDTO updateCart(@PathVariable Long id, @PathVariable int quantity) {

		return cartService.updateCart(id, quantity);
	}

	@DeleteMapping("/delete/{cartId}")
	public String deleteCartItem(@PathVariable Long cartId) {
		cartService.deleteCartItem(cartId);
		return "Deleted";
	}

	@DeleteMapping("/clear/{userId}")
	public String clearCart(@PathVariable Long userId) {
		cartService.clearCart(userId);
		return "Cart cleared";
	}
}