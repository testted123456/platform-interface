package com.nonobank.inter.controller;

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
import com.nonobank.inter.service.CodeCheckService;

@Controller
@RequestMapping(value = "codeCheck")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CodeCheckController {
	
	public static Logger logger = LoggerFactory.getLogger(CodeCheckController.class);
	
	@Autowired
	CodeCheckService codeCheckService;

	@GetMapping(value="check")
	@ResponseBody
	public Result checkCode(String system, String branch){
		codeCheckService.checkCode(system, branch);
		return ResultUtil.success();
	}
}
