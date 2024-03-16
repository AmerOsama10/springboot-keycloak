package com.learn.foodorder.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OrderItem{
	
	@Id
    @GeneratedValue
    public Long id;

    private Long orderId;

    private Long menuItemId;

    private BigDecimal price;
}