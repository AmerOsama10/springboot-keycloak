package com.learn.foodorder.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.foodorder.entity.Order;


public interface OrderRepository extends JpaRepository<Order, Long>{

	List<Order> findByRestaurantId(Long restaurantId);

}