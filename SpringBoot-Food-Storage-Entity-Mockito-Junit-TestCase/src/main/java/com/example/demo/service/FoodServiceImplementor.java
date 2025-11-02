package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Food;
import com.example.demo.repo.FoodRepo;

@Service
public class FoodServiceImplementor implements FoodService {

	@Autowired
	FoodRepo foodRepo;

	@Override
	public Food addToList(Food food) {

		Food save = foodRepo.save(food);

		return save;
	}

	@Override
	public List<Food> getAllFood() {
		List<Food> all = foodRepo.findAll();
		return all;
	}

	@Override
	public int checkFoodAvailablity(String fName) {
		int byAvailability = foodRepo.findByAvailability(fName);
		return byAvailability;
	}

	@Override
	public Food updateByName(Food food) {
		Food save = foodRepo.save(food);
		return save;
	}

	@Override
	public boolean deleteByName(String foodName) {
	boolean delete = foodRepo.delete(foodName);
		return delete;
	}

}
