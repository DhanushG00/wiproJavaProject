package com.wipro.order_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.order_service.dto.CartDTO;
import com.wipro.order_service.entity.Cart;
import com.wipro.order_service.entity.MenuEntity;
import com.wipro.order_service.repository.CartRepository;
import com.wipro.order_service.repository.MenuRepository;
import com.wipro.order_service.service.CartServiceImpl;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private Cart cart;
    private MenuEntity menu;

    @BeforeEach
    void setUp() {
        menu = new MenuEntity();
        menu.setId(1L);
        menu.setName("Pizza");
        menu.setPrice(200);
        menu.setCategory("Veg");

        cart = new Cart();
        cart.setId(10L);
        cart.setUserId(100L);
        cart.setMenu(menu);
        cart.setQuantity(2);
    }

    // addToCart - SUCCESS
    @Test
    void testAddToCart_Success() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        CartDTO result = cartService.addToCart(100L, 1L, 2);

        assertNotNull(result);
        assertEquals(100L, result.getUserId());
        assertEquals(2, result.getQuantity());
        assertEquals("Pizza", result.getMenu().getName());
    }

    // addToCart - MENU NOT FOUND
    @Test
    void testAddToCart_MenuNotFound() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            cartService.addToCart(100L, 1L, 2);
        });

        assertEquals("Menu not found", ex.getMessage());
    }

    // getCartByUser
    @Test
    void testGetCartByUser() {
        when(cartRepository.findByUserId(100L)).thenReturn(List.of(cart));

        List<CartDTO> result = cartService.getCartByUser(100L);

        assertEquals(1, result.size());
        assertEquals("Pizza", result.get(0).getMenu().getName());
    }

    // updateCart - SUCCESS
    @Test
    void testUpdateCart_Success() {
        when(cartRepository.findById(10L)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        CartDTO result = cartService.updateCart(10L, 5);

        assertEquals(5, result.getQuantity());
    }

    // updateCart - NOT FOUND
    @Test
    void testUpdateCart_NotFound() {
        when(cartRepository.findById(10L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            cartService.updateCart(10L, 5);
        });

        assertEquals("Cart not found", ex.getMessage());
    }

    // deleteCartItem
    @Test
    void testDeleteCartItem() {
        doNothing().when(cartRepository).deleteById(10L);

        cartService.deleteCartItem(10L);

        verify(cartRepository, times(1)).deleteById(10L);
    }

    // clearCart
    @Test
    void testClearCart() {
        doNothing().when(cartRepository).deleteByUserId(100L);

        cartService.clearCart(100L);

        verify(cartRepository, times(1)).deleteByUserId(100L);
    }
}
