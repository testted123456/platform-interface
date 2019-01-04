package com.nonobank.inter.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nonobank.inter.component.result.Result;
import com.nonobank.inter.component.result.ResultUtil;
import com.nonobank.inter.service.JenkinsService;

@Controller
@RequestMapping(value = "jenkins")
@CrossOrigin(origins = "*", maxAge = 3600)
public class JenkinsController {

	public static Logger logger = LoggerFactory.getLogger(JenkinsController.class);
	
	@Autowired
	JenkinsService jenkinsService;
	
	@GetMapping(value = "getPackageResult")
	@ResponseBody
	public Result getJenkinsPackageResult(){
		
		logger.info("开始获取jenkins job执行情况...");
		
		List<Map<String, String>> list = jenkinsService.getPackageResult();
		
		return ResultUtil.success(list);
		
	}

	
}
