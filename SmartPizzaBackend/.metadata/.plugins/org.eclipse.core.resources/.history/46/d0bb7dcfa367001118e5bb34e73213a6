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

import com.wipro.order_service.dto.MenuRequestDTO;
import com.wipro.order_service.dto.MenuResponseDTO;
import com.wipro.order_service.service.MenuService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    
    @PostMapping
    public MenuResponseDTO addMenu(@Valid @RequestBody MenuRequestDTO request) {
        return menuService.addMenu(request);
    }

    
    @GetMapping
    public List<MenuResponseDTO> getAllMenus() {
        return menuService.getAllMenus();
    }

   
    @GetMapping("/{id}")
    public MenuResponseDTO getMenu(@PathVariable Long id) {
        return menuService.getMenuById(id);
    }

    
    @PutMapping("/{id}")
    public MenuResponseDTO updateMenu(@PathVariable Long id,
                                      @Valid @RequestBody MenuRequestDTO request) {
        return menuService.updateMenu(id, request);
    }

   
    @DeleteMapping("/{id}")
    public String deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return "Menu deleted successfully";
    }
}