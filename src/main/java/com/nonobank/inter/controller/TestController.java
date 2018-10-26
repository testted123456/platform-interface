package com.nonobank.inter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nonobank.inter.component.result.Result;
import com.nonobank.inter.service.UserFeignService;

@Controller
@RequestMapping(value = "test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {

	@Autowired
	UserFeignService userFeignService;
	
	@GetMapping(value="/getAllRoles")
	public Result getAllRoles(){
		return userFeignService.getAllRoles();
	}
}
