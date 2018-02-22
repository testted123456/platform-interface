package com.nonobank.inter.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nonobank.inter.entity.Girl;
import com.nonobank.inter.service.GirlService;

@RestController
public class GirlController {
	
	@Autowired
	private GirlService girlService;
	
	@GetMapping(value="findOneGirl")
	public Girl findOne(@RequestParam("id") Integer id){
//		return girlService.findOne(id);
		Map<String, String> map =  new HashMap<String, String>();
		map.put("id", "1");
		map.put("age", "19");
		return girlService.findById(map);
	}

}
