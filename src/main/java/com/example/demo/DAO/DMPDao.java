package com.example.demo.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DMPDao {
	public String lastPetIndex();
	public int createPet(String owner);
	public List<String> petList(String owner);
}
