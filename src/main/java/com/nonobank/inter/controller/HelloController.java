package com.nonobank.inter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nonobank.inter.component.GirlProperties;

@RestController
public class HelloController {
	
	@Value("${girl.content}")
	private String content;
	
	@Autowired
	private GirlProperties girl;

	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public String say(){
		return content;
	}
}
