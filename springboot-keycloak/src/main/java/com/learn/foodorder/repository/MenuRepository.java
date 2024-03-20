package com.learn.foodorder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.foodorder.entity.Menu;


public interface MenuRepository  extends JpaRepository<Menu, Long>{

	Menu findByRestaurantId(Long restaurantId);
	
    List<Menu> findAllByRestaurantId(Long restaurantId);


}