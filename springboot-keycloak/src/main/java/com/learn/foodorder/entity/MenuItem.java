package com.learn.foodorder.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MenuItem {
	
	@Id
    @GeneratedValue
    public Long id;

    private Long menuId;

    private String name;

    private String description;

    @Column(name = "type_name")
    private String type;

    @Column(name = "group_name")
    private String group;

    private BigDecimal price;
}