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
import com.nonobank.inter.component.statistic.CaseStatistics;
import com.nonobank.inter.component.statistic.GroupStatistics;

@Controller
@RequestMapping(value = "statis")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StatisticController {
	
	public static Logger logger = LoggerFactory.getLogger(StatisticController.class);

	@Autowired
	ApiStatistics apiStatistics;
	
	@Autowired
	CaseStatistics caseStatistics;
	
	@Autowired
	GroupStatistics groupStatistics;
	
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
	
	@GetMapping(value="statisApiByModuleAndBranch")
	@ResponseBody
	public Result statisApiByModuleAndBranch(@RequestParam String system, @RequestParam String module){
		logger.info("开始统计分支接口数...");
		Map<String, List<?>> map =apiStatistics.ApiStatisGroupBySystemAndModule(system, module);
		return ResultUtil.success(map);
	}
	
	@GetMapping(value="statisGroupBySystemRef")
	@ResponseBody
	public Result statisGroupBySystemRef(){
		logger.info("开始统计分支被引用数...");
		Map<String, List<?>> map =apiStatistics.ApiStatisGroupBySystemRef();
		return ResultUtil.success(map);
	}
	
	@GetMapping(value="statisGroupBySystemAndModuleRef")
	@ResponseBody
	public Result statisGroupBySystemAndModuleRef(@RequestParam String system){
		logger.info("开始统计分支被引用数...");
		Map<String, List<?>> map =apiStatistics.ApiStatisGroupBySystemAndModuleRef(system);
		return ResultUtil.success(map);
	}
	
	@GetMapping(value="caseStatisGroupBySystem")
	@ResponseBody
	public Result caseStatisGroupBySystem(){
		logger.info("开始统计用例被数...");
		Map<String, List<?>> map = caseStatistics.caseStatisGroupBySystem();
		return ResultUtil.success(map);
	}
	
	@GetMapping(value="caseStatisGroupByRef")
	@ResponseBody
	public Result caseStatisGroupByRef(){
		logger.info("开始统计用例被被引用数...");
		Map<String, List<?>> map = caseStatistics.caseStatisGroupByRef();
		return ResultUtil.success(map);
	}
	
	@GetMapping(value="groupStatisGroupByGroup")
	@ResponseBody
	public Result groupStatisGroupByGroup(){
		logger.info("开始统计group中用例数...");
		Map<String, List<?>> map = groupStatistics.groupStatisGroupByGroup();
		return ResultUtil.success(map);
	}

	@GetMapping(value="groupStatisSuccessRate")
	@ResponseBody
	public Result groupStatisSuccessRate(){
		logger.info("开始统计测试集中用例执行成功率...");
		Map<String, List<?>> map = groupStatistics.groupStatisSuccessRate();
		return ResultUtil.success(map);
	}
	
	@GetMapping(value="groupStatisDetail")
	@ResponseBody
	public Result groupStatisDetail(){
		logger.info("开始统计用例执行统计...");
		List<Map<String, String>> map = groupStatistics.groupStatisDetail();
		return ResultUtil.success(map);
	}
}
