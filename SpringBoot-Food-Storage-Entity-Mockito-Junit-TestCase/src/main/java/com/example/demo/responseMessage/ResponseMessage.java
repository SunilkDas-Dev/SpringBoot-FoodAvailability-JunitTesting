package com.example.demo.responseMessage;

import java.util.List;

import com.example.demo.entity.Food;

import lombok.Data;
@Data
public class ResponseMessage {

	private Integer messageCode;
	private String message;
	private String errorshow;
	private List<Food> list;

	public ResponseMessage(Integer messageCode, String message, String errorshow, List<Food> list) {
		super();
		this.messageCode = messageCode;
		this.message = message;
		this.errorshow = errorshow;
		this.list = list;
	}

	public ResponseMessage(String message, String errorshow, List<Food> list) {
		super();
		this.message = message;
		this.errorshow = errorshow;
		this.list = list;
	}

	public ResponseMessage(String errorshow, String message) {
		super();
		this.errorshow = errorshow;
		this.message = message;
	}

}
