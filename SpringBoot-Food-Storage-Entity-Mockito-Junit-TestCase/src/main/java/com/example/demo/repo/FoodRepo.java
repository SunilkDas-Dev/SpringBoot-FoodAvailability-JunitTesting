package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Food;

public interface FoodRepo extends JpaRepository<Food, Long> {

	@Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END FROM Food WHERE LOWER(name) = LOWER(:name)", nativeQuery = true)
	int findByAvailability(String foodName);

	@Query(value="Delete  from Food where lower(foodName)=lower(:foodName)",nativeQuery = true)
	boolean delete(String foodName);

}
