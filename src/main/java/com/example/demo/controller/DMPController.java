package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DMPService;
import com.example.demo.vo.Pet;
import java.util.List;


@RestController
public class DMPController {

	@Autowired
	DMPService dmpService;

	@GetMapping("test")
	public String test() {
		dmpService.lastPetIndex();
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
	public List<String> querySubmit(HttpServletRequest request) throws Exception {
		String id = "2";
		return dmpService.querySubmit(id);
	}
	
	@PostMapping("createPet")
	public String createPet(HttpServletRequest req) {
		//@ModelAttribute Pet pet
		String pet_name = req.getParameter("pet_name");
		String pet_owner = "2";
		String pet_gender = req.getParameter("pet_gender");
		String pet_breed = req.getParameter("pet_breed");
		String pet_birth = req.getParameter("pet_birth");
		Pet pet= new Pet(pet_name,pet_owner,pet_gender,pet_breed,pet_birth);
		System.out.println("createPet");
		System.out.println(pet.getPet_name());
	
		try {

			return dmpService.createPet(pet);
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}


}