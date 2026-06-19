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

import com.wipro.order_service.dto.MenuDTO;
import com.wipro.order_service.entity.MenuEntity;
import com.wipro.order_service.repository.MenuRepository;
import com.wipro.order_service.service.MenuServiceImpl;

@ExtendWith(MockitoExtension.class)
class MenuServiceImplTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuServiceImpl menuService;

    private MenuEntity menuEntity;
    private MenuDTO menuDTO;

    @BeforeEach
    void setUp() {
        menuEntity = new MenuEntity();
        menuEntity.setId(1L);
        menuEntity.setName("Pizza");
        menuEntity.setDescription("Delicious pizza");
        menuEntity.setPrice(200);
        menuEntity.setCategory("Veg");
        menuEntity.setImageUrl("img.png");

        menuDTO = new MenuDTO();
        menuDTO.setName("Pizza");
        menuDTO.setDescription("Delicious pizza");
        menuDTO.setPrice(200);
        menuDTO.setCategory("Veg");
        menuDTO.setImageUrl("img.png");
    }

    // addMenu
    @Test
    void testAddMenu() {
        when(menuRepository.save(any(MenuEntity.class))).thenReturn(menuEntity);

        MenuDTO result = menuService.addMenu(menuDTO);

        assertNotNull(result);
        assertEquals("Pizza", result.getName());
        assertEquals(200, result.getPrice());
    }

    // getAllMenu
    @Test
    void testGetAllMenu() {
        when(menuRepository.findAll()).thenReturn(List.of(menuEntity));

        List<MenuDTO> result = menuService.getAllMenu();

        assertEquals(1, result.size());
        assertEquals("Pizza", result.get(0).getName());
    }

    // getMenuById - SUCCESS
    @Test
    void testGetMenuById_Success() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menuEntity));

        MenuDTO result = menuService.getMenuById(1L);

        assertEquals("Pizza", result.getName());
    }

    // getMenuById - NOT FOUND
    @Test
    void testGetMenuById_NotFound() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            menuService.getMenuById(1L);
        });

        assertEquals("Menu not found", ex.getMessage());
    }

    // updateMenu - SUCCESS
    @Test
    void testUpdateMenu_Success() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menuEntity));
        when(menuRepository.save(any(MenuEntity.class))).thenReturn(menuEntity);

        MenuDTO result = menuService.updateMenu(1L, menuDTO);

        assertEquals("Pizza", result.getName());
        assertEquals("Veg", result.getCategory());
    }

    // updateMenu - NOT FOUND
    @Test
    void testUpdateMenu_NotFound() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            menuService.updateMenu(1L, menuDTO);
        });

        assertEquals("Menu not found", ex.getMessage());
    }

    // deleteMenu
    @Test
    void testDeleteMenu() {
        doNothing().when(menuRepository).deleteById(1L);

        menuService.deleteMenu(1L);

        verify(menuRepository, times(1)).deleteById(1L);
    }
}

