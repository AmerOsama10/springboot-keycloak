package com.learn.foodorder.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.foodorder.entity.Menu;
import com.learn.foodorder.entity.MenuItem;
import com.learn.foodorder.entity.Restaurant;
import com.learn.foodorder.repository.MenuItemRepository;
import com.learn.foodorder.repository.MenuRepository;
import com.learn.foodorder.repository.RestaurantRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;



@RestController
@RequestMapping("/restaurant")
@SecurityRequirement(name = "Keycloak")
public class RestaurantController {
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Autowired
	MenuRepository menuRepository;
	
	@Autowired
	MenuItemRepository menuItemRepository;

	@GetMapping("/public/list")
	public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }
	
	@GetMapping("/public/menu/{restaurantId}")
	public Menu getMenu(@PathVariable Long restaurantId) {
		List<Menu> allMenus  = menuRepository.findAllByRestaurantId(restaurantId);
		 Menu menu =null;
        if (!allMenus.isEmpty()) {
            menu = allMenus.get(allMenus.size() - 1);
            List<MenuItem> menuItems = menuItemRepository.findByMenuId(menu.getId());
            menu.setMenuItems(menuItems);
        }
        return menu;
    }
	
	@PostMapping
	@PreAuthorize("hasRole('admin')")
	public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
	
	@PostMapping("/menu")
	@PreAuthorize("hasRole('manager')")
	public Menu createMenu(@RequestBody Menu menu) {
		menuRepository.save(menu);
        menu.getMenuItems().forEach(menuItem -> {
            menuItem.setMenuId(menu.id);
            menuItemRepository.save(menuItem);
        });
        return menu;
    }
	
	@PutMapping("/menu/item/{itemId}/{price}")
	@PreAuthorize("hasRole('owner')")
	public MenuItem updateMenuItemPrice(@PathVariable Long itemId
            , @PathVariable BigDecimal price) {
        Optional<MenuItem> menuItem = menuItemRepository.findById(itemId);
        menuItem.get().setPrice(price);
        menuItemRepository.save(menuItem.get());
        return menuItem.get();
    }
}