package com.learn.foodorder.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")

public class Order{
	
	@Id
    @GeneratedValue
    public Long id;

    private Long restaurantId;

    private BigDecimal total;

    @Transient
    private List<OrderItem> orderItems;
}