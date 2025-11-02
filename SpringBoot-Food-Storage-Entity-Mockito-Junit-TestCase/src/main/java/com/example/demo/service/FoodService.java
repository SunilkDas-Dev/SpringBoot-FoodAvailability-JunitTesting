package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Food;

public interface FoodService {

	Food addToList(Food food);

	List<Food> getAllFood();

	int checkFoodAvailablity(String foodName);

	Food updateByName(Food foodName);

	boolean deleteByName(String foodName);

}
