package com.learn.foodorder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.foodorder.entity.Order;
import com.learn.foodorder.entity.OrderItem;
import com.learn.foodorder.repository.OrderItemRepository;
import com.learn.foodorder.repository.OrderRepository;


@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	@GetMapping
	@RequestMapping("/{restaurantId}/list")
	// manager can access (suresh)
	public List<Order> getOrders(@PathVariable Long restaurantId) {
		return orderRepository.findByRestaurantId(restaurantId);
    }
	
	@GetMapping
	@RequestMapping("/{orderId}")
	// manager can access (suresh)
	public Order getOrderDetails(@PathVariable Long orderId) {
		Order order = orderRepository.findById(orderId).orElse(null);
		if(order != null) {
        order.setOrderItems(orderItemRepository.findByOrderId(order.getId()));
		}
        return order;
    }
	
	@PostMapping
	// authenticated users can access
	public Order createOrder(@RequestBody Order order) {
		orderRepository.save(order);
        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.forEach(orderItem -> {
            orderItem.setOrderId(order.id);
            orderItemRepository.save(orderItem);
        });
        return order;
    }

}