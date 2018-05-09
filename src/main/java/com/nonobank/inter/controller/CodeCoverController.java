package com.nonobank.inter.controller;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nonobank.inter.component.exception.ApiException;
import com.nonobank.inter.component.result.Result;
import com.nonobank.inter.component.result.ResultCode;
import com.nonobank.inter.component.result.ResultUtil;
import com.nonobank.inter.service.CodeCoverService;

@Controller
@RequestMapping(value="codeCover")
public class CodeCoverController {

	public static Logger logger = LoggerFactory.getLogger(CodeCoverController.class);
	
	@Value("${jacocoReportPath}")
    private String jacocoReportPath;
	
	@Value("${jacocoDumpath}")
    private String jacocoDumpath;
	
	@Value("${codePath}")
    private String codePath;
	
	@Autowired
	private CodeCoverService codeCoverService;
	
	@GetMapping(value="generateReport")
	@ResponseBody
	public Result generateReport(@RequestParam String system, @RequestParam String branch, @RequestParam String ip, @RequestParam Integer port){
		logger.info("开始生成jacoco覆盖率报告，系统:{},分支:{}", system, branch);
		
		ApplicationHome home = new ApplicationHome(this.getClass());
        File branchCodeDir = new File(home.getDir(), String.format("%s/%s/%s", codePath, system, branch));
        
        if(!branchCodeDir.exists()){
        	logger.error("源码还没拉取！");
        	return ResultUtil.error(ResultCode.VALIDATION_ERROR.getCode(), "源码还没拉取！");
        }
        
		try {
			codeCoverService.generateReport(jacocoDumpath, system, branch, jacocoReportPath, ip, port, system + "覆盖率报告");
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
			throw new ApiException(ResultCode.EXCEPTION_ERROR.getCode(), "生成jacoco覆盖率报告异常！");
		}
		return ResultUtil.success();
	}

}
