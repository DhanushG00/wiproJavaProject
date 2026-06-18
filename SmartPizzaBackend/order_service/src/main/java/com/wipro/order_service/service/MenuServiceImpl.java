package com.wipro.order_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.order_service.dto.MenuDTO;
import com.wipro.order_service.entity.MenuEntity;
import com.wipro.order_service.repository.MenuRepository;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public MenuDTO addMenu(MenuDTO dto) {

        MenuEntity entity = new MenuEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setCategory(dto.getCategory());
        entity.setImageUrl(dto.getImageUrl());

        return convertToDTO(menuRepository.save(entity));
    }

    @Override
    public List<MenuDTO> getAllMenu() {
        return menuRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public MenuDTO getMenuById(Long id) {
        MenuEntity entity = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        return convertToDTO(entity);
    }

    @Override
    public MenuDTO updateMenu(Long id, MenuDTO dto) {

        MenuEntity entity = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setCategory(dto.getCategory());
        entity.setImageUrl(dto.getImageUrl());

        return convertToDTO(menuRepository.save(entity));
    }

    @Override
    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }

    //  ENTITY → DTO
    private MenuDTO convertToDTO(MenuEntity entity) {

        MenuDTO dto = new MenuDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setCategory(entity.getCategory());
        dto.setImageUrl(entity.getImageUrl());

        return dto;
    }
}