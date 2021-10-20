package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DMPService;
import com.example.demo.vo.Pet;

@RestController
public class DMPController {

	@Autowired
	DMPService dmpService;

	@GetMapping("test")
	public String test() {
		System.out.println("test");
		return "test ok";
	}
	
	@PostMapping("queryAllPet")
	public String queryAllPet() {
		System.out.println("queryAllPet");
		try {

			String result = dmpService.queryAllPet();
			System.out.println("{\"info\":" + result + "}");
			return "{\"info\":" + result + "}";

		} catch (Exception e) {
			return "fail";
		}
	}
	
	@PostMapping("querySubmit")
	public String querySubmit(HttpServletRequest request) {
		try {
			String petNumber = request.getParameter("petNumber");
			String result = dmpService.querySubmit(petNumber);
			System.out.println("{\"info\":" + result + "}");
			return "{\"info\":" + result + "}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
	}
	
	@PostMapping("createPet")
	public String createPet(@ModelAttribute Pet pet) {
		// HttpServletRequest로 불러와서 request.getParameter 5번하는게 더 빠름
		System.out.println("createPet");
		System.out.println(pet.getPet_name());
		try {

			return dmpService.createPet(pet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
	}

	@PostMapping("transferSubmit")
	public String transferSubmit(HttpServletRequest request) {
		// HttpServletRequest로 불러와서 request.getParameter 5번하는게 더 빠름
		System.out.println("transferSubmit");
		String car_id2 = request.getParameter("car_id2");
		String car_owner2 = request.getParameter("car_owner2");

		try {
			return dmpService.transferSubmit(car_id2, car_owner2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
	}


}