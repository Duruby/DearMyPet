package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DMPService;

@RestController
public class DMPController {

	@Autowired
	DMPService fabCarService;

	@GetMapping("test")
	public String test() {
		System.out.println("test");
		return "test ok";
	}


}