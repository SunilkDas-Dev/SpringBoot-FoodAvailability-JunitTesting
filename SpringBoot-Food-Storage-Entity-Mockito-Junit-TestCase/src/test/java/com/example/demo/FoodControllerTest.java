package com.example.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.FoodController;
import com.example.demo.entity.Food;
import com.example.demo.service.FoodService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(FoodController.class)
public class FoodControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FoodService foodService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void foodInsertionTest() throws Exception {
		Food f1 = new Food();
		f1.setFoodId(1L);
		f1.setFoodName("Biryani");
		f1.setFoodType("NonVeg/Heavy");
		f1.setAvailability(true);
		f1.setPrice(235D);

		Food f2 = new Food();
		f2.setFoodId(2L);
		f2.setFoodName("Pulav");
		f2.setFoodType("Veg/Heavy");
		f2.setAvailability(true);
		f2.setPrice(230D);

		when(foodService.addToList(f1)).thenReturn(f1);

		mockMvc.perform(post("/food/insert").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(f1))).andExpect(status().isAccepted())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	@Test
	public void getAll() throws Exception {
		Food f1 = new Food();
		f1.setFoodId(1L);
		f1.setFoodName("Biryani");
		f1.setFoodType("NonVeg/Heavy");
		f1.setAvailability(true);
		f1.setPrice(235D);

		Food f2 = new Food();
		f2.setFoodId(2L);
		f2.setFoodName("Pulav");
		f2.setFoodType("Veg/Heavy");
		f2.setAvailability(true);
		f2.setPrice(230D);

		List<Food> finalList = new ArrayList<>();
		finalList.add(f1);
		finalList.add(f2);

		when(foodService.getAllFood()).thenReturn(finalList);

		mockMvc.perform(get("/food/getAll").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	public void testCheckAvailability_FoodAvailable() throws Exception {
		// Mock service layer
		when(foodService.checkFoodAvailablity("Pulav")).thenReturn(1);

		mockMvc.perform(get("/food/checkAvailability/Pulav").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("Pulav is available right now"))
				.andExpect(jsonPath("$.errorshow").value("Success"));
	}

	@Test
	public void testCheckAvailability_FoodNotAvailable() throws Exception {
		when(foodService.checkFoodAvailablity("Pizza")).thenReturn(0);

		mockMvc.perform(get("/food/checkAvailability/Pizza").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isGone()).andExpect(jsonPath("$.message").value("Pizza Is Not Available Right Now"))
				.andExpect(jsonPath("$.errorshow").value("Failed"));
	}

	@Test
	public void updateFoodData() throws Exception {

		Food f1 = new Food();
		f1.setFoodId(1L);
		f1.setFoodName("Fried Rice");
		f1.setFoodType("Veg/Heavy");
		f1.setAvailability(true);
		f1.setPrice(250D);

		when(foodService.checkFoodAvailablity("Fried Rice")).thenReturn(2);
		when(foodService.updateByName(f1)).thenReturn(f1);
		mockMvc.perform(put("/food/updatefood/{foodName}", "Fried Rice").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(f1))).andExpect(status().isOk())
				// .andExpect(jsonPath("$.messageCode").value("success"))
				.andExpect(jsonPath("$.message").value(" Data updated Successfully"));
	}

	@Test
	public void testDeleteFood_Success() throws Exception {
		String foodName = "Fried Rice";

		when(foodService.checkFoodAvailablity(foodName)).thenReturn(2); // must be > 1
		when(foodService.deleteByName(foodName)).thenReturn(true);

		mockMvc.perform(delete("/food/delete/{foodName}", foodName).contentType(MediaType.APPLICATION_JSON));

	}

}
