package com.wipro.order_service.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.order_service.dto.MenuRequestDTO;
import com.wipro.order_service.dto.MenuResponseDTO;
import com.wipro.order_service.entity.MenuEntity;
import com.wipro.order_service.exception.ResourceNotFoundException;
import com.wipro.order_service.repository.MenuRepository;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    
    @Override
    public MenuResponseDTO addMenu(MenuRequestDTO request) {

        MenuEntity entity = mapToEntity(request);
        return mapToResponse(menuRepository.save(entity));
    }

   
    @Override
    public List<MenuResponseDTO> getAllMenus() {

        return menuRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    
    @Override
    public MenuResponseDTO getMenuById(Long id) {

        MenuEntity entity = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + id));

        return mapToResponse(entity);
    }

    
    @Override
    public MenuResponseDTO updateMenu(Long id, MenuRequestDTO request) {

        MenuEntity entity = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setCategory(request.getCategory());
        entity.setImageUrl(request.getImageUrl());

        return mapToResponse(menuRepository.save(entity));
    }

   
    @Override
    public void deleteMenu(Long id) {

        MenuEntity entity = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));

        menuRepository.delete(entity);
    }

    // Mapping Methods
    private MenuEntity mapToEntity(MenuRequestDTO dto) {
        return MenuEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .imageUrl(dto.getImageUrl())
                .build();
    }

    private MenuResponseDTO mapToResponse(MenuEntity entity) {
        return MenuResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .category(entity.getCategory())
                .imageUrl(entity.getImageUrl())
                .build();
    }
}