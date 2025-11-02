package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {

	@Id
	private Long foodId;

	@Column(name = "name")
	private String foodName;

	@Column(name = "type")
	private String foodType;

	@Column(name = "available")
	private boolean availability;

	@Column(name = "price")
	private double price;

}
