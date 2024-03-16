package com.learn.foodorder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Restaurant {

	@Id
    @GeneratedValue
    public Long id;
	
    private String name;

    private String location;

    @Column(name = "type_name")
    private String type;
}