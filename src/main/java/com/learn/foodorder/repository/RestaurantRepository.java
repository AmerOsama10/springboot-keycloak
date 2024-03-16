package com.learn.foodorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.foodorder.entity.Restaurant;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
}