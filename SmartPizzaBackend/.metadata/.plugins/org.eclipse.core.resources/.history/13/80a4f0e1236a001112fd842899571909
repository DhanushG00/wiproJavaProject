package com.wipro.order_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wipro.order_service.dto.MenuDTO;
import com.wipro.order_service.service.MenuService;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@PostMapping("/add")
	public MenuDTO addMenu(@RequestBody MenuDTO dto) {
		return menuService.addMenu(dto);
	}

	@GetMapping("/all")
	public List<MenuDTO> getAllMenu() {
		return menuService.getAllMenu();
	}

	@GetMapping("/{id}")
	public MenuDTO getMenuById(@PathVariable Long id) {
		return menuService.getMenuById(id);
	}

	@PutMapping("/update/{id}")
	public MenuDTO updateMenu(@PathVariable Long id, @RequestBody MenuDTO dto) {
		return menuService.updateMenu(id, dto);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteMenu(@PathVariable Long id) {
		menuService.deleteMenu(id);
		return "Menu deleted";
	}
}
