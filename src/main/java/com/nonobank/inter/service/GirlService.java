package com.nonobank.inter.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.nonobank.inter.entity.Girl;
import com.nonobank.inter.repository.GirlRepository;

@Service
public class GirlService {
	
	@Autowired
	private GirlRepository girlRepository;
	
	public Girl findOne(Integer id){
//		girlRepository.findAll(pageable)
		return girlRepository.findOne(id);
	}
	
	public Girl findById(Map<String, String> map){
		Integer id = Integer.valueOf(map.get("id"));
		Integer age = Integer.valueOf(map.get("age"));
		age = null;
		return girlRepository.findByIdAndAge(id, age);
	}

}
