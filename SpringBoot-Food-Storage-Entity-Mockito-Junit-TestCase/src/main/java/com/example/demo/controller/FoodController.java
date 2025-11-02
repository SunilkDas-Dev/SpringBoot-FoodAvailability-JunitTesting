package com.example.demo.controller;

import java.net.HttpURLConnection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constantMessage.ConstantMessage;
import com.example.demo.entity.Food;
import com.example.demo.responseMessage.ResponseMessage;
import com.example.demo.service.FoodService;

@RestController
@RequestMapping("/food")

public class FoodController {

	@Autowired
	FoodService foodService;

	@PostMapping("/insert")
	public ResponseEntity<ResponseMessage> addFood(@RequestBody Food food) {
		try {

			Food toList = foodService.addToList(food);

			if (toList.getFoodId() == null || toList.getFoodName().isBlank() || toList.getFoodName().isEmpty()
					|| toList.getFoodType().isBlank() || toList.getFoodType().isEmpty() || toList.getPrice() <= 0.0) {
				return ResponseEntity.status(HttpURLConnection.HTTP_BAD_REQUEST).body(new ResponseMessage(
						ConstantMessage.FAILED, " You have Entered Something Empty .. Try To Fill All The Task"));

			}

			if (toList.isAvailability()) {
				return ResponseEntity.status(HttpURLConnection.HTTP_ACCEPTED)
						.body(new ResponseMessage(ConstantMessage.SUCCESS, " Food  added Successfully"));
			} else {

				return ResponseEntity.status(HttpURLConnection.HTTP_BAD_REQUEST)
						.body(new ResponseMessage(ConstantMessage.FAILED, " Food Insertion Failed."));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpURLConnection.HTTP_CONFLICT)
					.body(new ResponseMessage(ConstantMessage.FAILURE, " Something Error Occures Try Again Later "));
		}
	}

	@GetMapping("/getAll")
	public ResponseEntity<ResponseMessage> getAllFoodDetails() {

		try {
			List<Food> allFood = foodService.getAllFood();
			if (allFood.isEmpty()) {
				return ResponseEntity.status(HttpURLConnection.HTTP_CONFLICT)
						.body(new ResponseMessage(ConstantMessage.FAILED, " There is No Data to Show"));
			} else {
				return ResponseEntity.status(HttpURLConnection.HTTP_OK)
						.body(new ResponseMessage(ConstantMessage.SUCCESS, "Data Fetching Successful", allFood));
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpURLConnection.HTTP_CONFLICT).body(
					new ResponseMessage(ConstantMessage.FAILURE, " Something Went Wrong During Fetching The Data"));
		}

	}

	@GetMapping("/checkAvailability/{foodName}")
	public ResponseEntity<ResponseMessage> checkAvailability(@PathVariable String foodName) {

		try {
			int checkFoodAvailablity = foodService.checkFoodAvailablity(foodName);

			if (checkFoodAvailablity > 0) {
				return ResponseEntity.status(HttpURLConnection.HTTP_OK)
						.body(new ResponseMessage(ConstantMessage.SUCCESS, foodName + " is available right now"));
			}

			return ResponseEntity.status(HttpURLConnection.HTTP_GONE)
					.body(new ResponseMessage(ConstantMessage.FAILED, foodName + " Is Not Available Right Now"));

		} catch (Exception e) {
			return ResponseEntity.status(HttpURLConnection.HTTP_BAD_REQUEST)
					.body(new ResponseMessage(ConstantMessage.FAILURE, " Internal Server Error "));

		}
	}

	@PutMapping("/updatefood/{foodName}")
	public ResponseEntity<ResponseMessage> updateFood(@PathVariable String foodName, @RequestBody Food food) {
		try {
			int checkFoodAvailablity = foodService.checkFoodAvailablity(foodName);
			if (checkFoodAvailablity > 1) {
				Food updateByName = foodService.updateByName(food);
				if (updateByName != null) {
					return ResponseEntity.status(HttpURLConnection.HTTP_OK)
							.body(new ResponseMessage(ConstantMessage.SUCCESS, " Data updated Successfully"));
				} else {
					return ResponseEntity.status(HttpURLConnection.HTTP_BAD_REQUEST)
							.body(new ResponseMessage(ConstantMessage.FAILED, " Data updation failed"));
				}
			} else {
				return ResponseEntity.status(HttpURLConnection.HTTP_NOT_FOUND).body(
						new ResponseMessage(ConstantMessage.FAILED, "The Food Name You entered is not Available"));
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpURLConnection.HTTP_BAD_REQUEST)
					.body(new ResponseMessage(ConstantMessage.FAILURE, " Error Occures During Fetching The Data"));
		}
	}

	@DeleteMapping("/delete/foodName{}")
	public ResponseEntity<ResponseMessage> deleteFromMenu(@PathVariable String foodName) {

		try {
			int checkFoodAvailablity = foodService.checkFoodAvailablity(foodName);
			if (checkFoodAvailablity > 1) {
				boolean deleteByName = foodService.deleteByName(foodName);
				if (deleteByName) {
					return ResponseEntity.status(HttpURLConnection.HTTP_OK)
							.body(new ResponseMessage(ConstantMessage.SUCCESS, " Data deleted Successfully"));

				} else {
					return ResponseEntity.status(HttpURLConnection.HTTP_NO_CONTENT)
							.body(new ResponseMessage(ConstantMessage.FAILED, " Data Deletion Failed"));
				}
			} else {
				return ResponseEntity.status(HttpURLConnection.HTTP_NOT_FOUND)
						.body(new ResponseMessage(ConstantMessage.FAILED, " Food Name you have entered is not found"));
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpURLConnection.HTTP_CONFLICT)
					.body(new ResponseMessage(ConstantMessage.FAILURE, " Internal Error occures"));
		}
	}

}
