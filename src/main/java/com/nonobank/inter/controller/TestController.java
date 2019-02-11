package com.nonobank.inter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nonobank.inter.component.result.Result;
import com.nonobank.inter.component.result.ResultUtil;
import com.nonobank.inter.entity.TestEntity;
import com.nonobank.inter.repository.TestEntityRepository;
import com.nonobank.inter.service.UserFeignService;

@Controller
@RequestMapping(value = "test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {
	
	@Autowired
	UserFeignService userFeignService;
	
	@Autowired
	TestEntityRepository teRep;
	
	@GetMapping(value="/getAllRoles")
	public Result getAllRoles(){
		return userFeignService.getAllRoles();
	}
	
	@PostMapping(value="/save")
	@ResponseBody
	public Result save(@RequestBody TestEntity testEntity){
//		String str = "[{\"a\":1}]";
//		JSONArray jsonArray = JSONArray.parseArray(str);
//		testEntity.setJsonArray(jsonArray);
		teRep.save(testEntity);
		return ResultUtil.success();
	}
}
