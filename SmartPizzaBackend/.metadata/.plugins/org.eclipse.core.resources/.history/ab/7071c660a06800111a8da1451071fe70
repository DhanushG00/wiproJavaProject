package com.wipro.order_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.order_service.dto.CartRequestDTO;
import com.wipro.order_service.dto.CartResponseDTO;
import com.wipro.order_service.entity.CartEntity;
import com.wipro.order_service.exception.ResourceNotFoundException;
import com.wipro.order_service.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

   
    @Override
    public CartResponseDTO addToCart(CartRequestDTO request) {
        CartEntity entity = mapToEntity(request);
        return mapToResponse(cartRepository.save(entity));
    }

   
    @Override
    public List<CartResponseDTO> getCartByUser(Long userId) {
        return cartRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    
    @Override
    public CartResponseDTO updateCart(Long id, CartRequestDTO request) {

        CartEntity entity = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        entity.setUserId(request.getUserId());
        entity.setMenuId(request.getMenuId());
        entity.setQuantity(request.getQuantity());

        return mapToResponse(cartRepository.save(entity));
    }

    
    @Override
    public void deleteCartItem(Long id) {

        CartEntity entity = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        cartRepository.delete(entity);
    }

    
    @Override
    public void clearCart(Long userId) {

        List<CartEntity> list = cartRepository.findByUserId(userId);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Cart is already empty");
        }

        cartRepository.deleteAll(list);
    }

   
    private CartEntity mapToEntity(CartRequestDTO dto) {
        return CartEntity.builder()
                .userId(dto.getUserId())
                .menuId(dto.getMenuId())
                .quantity(dto.getQuantity())
                .build();
    }

    private CartResponseDTO mapToResponse(CartEntity entity) {
        return CartResponseDTO.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .menuId(entity.getMenuId())
                .quantity(entity.getQuantity())
                .build();
    }
}