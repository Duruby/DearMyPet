package com.example.demo.vo;

public class Pet {
	private String pet_name, pet_owner, pet_gender, pet_breed, pet_birth;	
	public Pet() {
	}
	
	public Pet(String pet_name, String pet_owner, String pet_gender, String pet_breed, String pet_birth) {
		this.pet_name = pet_name;
		this.pet_owner = pet_owner;
		this.pet_gender = pet_gender;
		this.pet_breed = pet_breed;
		this.pet_birth = pet_birth;
	}

	public String getPet_name() {
		return pet_name;
	}

	public String getPet_owner() {
		return pet_owner;
	}

	public String getPet_gender() {
		return pet_gender;
	}

	public String getPet_breed() {
		return pet_breed;
	}

	public String getPet_birth() {
		return pet_birth;
	}

	public void setPet_name(String pet_name) {
		this.pet_name = pet_name;
	}

	public void setPet_owner(String pet_owner) {
		this.pet_owner = pet_owner;
	}

	public void setPet_gender(String pet_gender) {
		this.pet_gender = pet_gender;
	}

	public void setPet_breed(String pet_breed) {
		this.pet_breed = pet_breed;
	}

	public void setPet_birth(String pet_birth) {
		this.pet_birth = pet_birth;
	}
}
