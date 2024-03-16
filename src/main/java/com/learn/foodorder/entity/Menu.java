	package com.learn.foodorder.entity;
	
	import java.util.List;
	
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.Id;
	import jakarta.persistence.Transient;
	import lombok.Getter;
	import lombok.Setter;
	
	@Getter
	@Setter
	@Entity
	public class Menu {
	@Id
	@GeneratedValue
		public long id;
		private long restaurantId;
		private boolean active;
		@Transient
		private List<MenuItem> menuItems;
	}
