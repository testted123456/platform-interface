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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nonobank.inter.component.result.Result;
import com.nonobank.inter.component.result.ResultUtil;
import com.nonobank.inter.component.statistic.ApiStatistics;

@Controller
@RequestMapping(value = "statis")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StatisticController {
	
	public static Logger logger = LoggerFactory.getLogger(StatisticController.class);

	@Autowired
	ApiStatistics apiStatistics;
	
	@GetMapping(value="statisApiBySys")
	@ResponseBody
	public Result statisApiBySys(){
		logger.info("开始统计系统接口数...");
		Map<String, List<?>> map =apiStatistics.statisApiGroupBySystem();
		return ResultUtil.success(map);
	}
	
	@GetMapping(value="statisApiByModule")
	@ResponseBody
	public Result statisApiByModule(@RequestParam String system){
		logger.info("开始统计模块接口数...");
		Map<String, List<?>> map =apiStatistics.statisApiGroupByModule(system);
		return ResultUtil.success(map);
	}

}
