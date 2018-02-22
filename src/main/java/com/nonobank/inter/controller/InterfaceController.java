package com.nonobank.inter.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nonobank.inter.component.MessageEntity;
import com.nonobank.inter.component.validation.ErrorCode;
import com.nonobank.inter.component.validation.InterfaceValidation;
import com.nonobank.inter.entity.InterfaceDefinition;
import com.nonobank.inter.entity.InterfaceDefinitionFront;
import com.nonobank.inter.service.InterfaceDefinitionService;

@Controller
@RequestMapping(value="api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InterfaceController {
	
	public static Logger logger = LoggerFactory.getLogger(InterfaceController.class);
	
	@Autowired
	InterfaceDefinitionService interfaceDefinitionService;
	
	/**
	 * 新增api目录节点
	 * @param interfaceDefinition
	 * @return
	 */
	@PostMapping(value="addApiDir")
	@ResponseBody
	public MessageEntity addDir(@RequestBody InterfaceDefinition interfaceDefinition){
		logger.info("开始新增接口目录");
		
		MessageEntity msg = new MessageEntity();
		Optional<InterfaceDefinition> optApi = Optional.ofNullable(interfaceDefinition);
		
		Optional<InterfaceDefinition> addedOptApi = optApi.map(x->{
//			x.setApiType('0');
			x.setOptstatus((short) 0);
			x.setType(true);
			
			try{
				interfaceDefinitionService.add(x);
				logger.info("新增接口目录成功");
				msg.setErrorCode(ErrorCode.correct);
				msg.setSucceed(true);
				msg.setData(interfaceDefinition);
			}catch(Exception e){
				logger.error("新增接口目录失败");
				msg.setErrorCode(ErrorCode.exception_occurred_error);
				msg.setSucceed(false);
				e.printStackTrace();
			}
			
			return x;
		});
		
		if(!addedOptApi.isPresent()){
			msg.setSucceed(false);
			msg.setErrorCode(ErrorCode.entity_empty_error);
			msg.setErrorMessage("接口信息为空");
		}
		
		return msg;
		
	}
	
	@PostMapping(value="addApi1")
	@ResponseBody
	public MessageEntity addApi1(@RequestBody InterfaceDefinitionFront api){
		MessageEntity msg = new MessageEntity();
		
		return msg;
	}
	
	/**
	 * 新增api
	 * @param interfaceDetail
	 * @return
	 */
	@PostMapping(value="addApi")
	@ResponseBody
	public MessageEntity addApi(@RequestBody InterfaceDefinition interfaceDefinition){
		logger.info("开始新增接口");
		
		MessageEntity msg = new MessageEntity();
		String result = InterfaceValidation.validate(interfaceDefinition);
		
		if("success".equals(result)){
			try{
				interfaceDefinition.setOptstatus((short)0);
				InterfaceDefinition api = interfaceDefinitionService.add(interfaceDefinition);
				logger.info("新增接口成功");
				msg.setSucceed(true);
				msg.setData(api);
				return msg;
			}catch(Exception e){
				logger.error("新增接口目录失败");
				msg.setErrorCode(ErrorCode.exception_occurred_error);
				msg.setErrorMessage("数据库操作异常！");
				msg.setSucceed(false);
				e.printStackTrace();
				return msg;
			}
		}else{
			logger.error("新增接口校验失败");
			msg.setSucceed(false);
			msg.setErrorCode(ErrorCode.validation_error);
			msg.setErrorMessage(result);
			return msg;
		}
	}
	
	@PostMapping(value="updateApi")
	@ResponseBody
	public MessageEntity updateApi(@RequestBody InterfaceDefinition interfaceDefinition){
		logger.info("开始修改api信息");
		
		MessageEntity msg = new MessageEntity();
		
		try{
			Optional<InterfaceDefinition> optApi = interfaceDefinitionService.findById(Optional.ofNullable(interfaceDefinition.getId()));
			Optional<InterfaceDefinition> updatedOptApi = optApi.map(a->interfaceDefinitionService.update(a));
			
			if(updatedOptApi.isPresent()){
				logger.info("修改api信息成功");
				msg.setSucceed(true);
				msg.setData(updatedOptApi.get());
			}else{
				logger.error("更新api失败");
				msg.setSucceed(false);
				msg.setErrorCode(ErrorCode.entity_empty_error);
				msg.setErrorMessage("更新api失败, api不存在");
			}
			
			return msg;
		}catch(Exception e){
			logger.error("更新api失败");
			msg.setSucceed(false);
			msg.setErrorCode(ErrorCode.operator_db_error);
			msg.setErrorMessage("更新api失败");
			e.printStackTrace();
			return msg;
		}
	}
	
	/**
	 * 获取api目录节点
	 * @param id
	 * @return
	 */
	@GetMapping(value="getApi")
	@ResponseBody
	public MessageEntity getApi(@RequestParam Integer id){
		MessageEntity msg = new MessageEntity();
		
		logger.info("开始查询api信息");
		
		try{
			InterfaceDefinition interfaceDefinition = interfaceDefinitionService.findById(id);
			msg.setSucceed(true);
			msg.setData(interfaceDefinition);
		}catch(Exception e){
			logger.error("查询api信息失败");
			msg.setSucceed(false);
			msg.setErrorCode(ErrorCode.common_error);
			msg.setErrorMessage("查询api目录信息失败");
		}
		
		return msg;
	}
	
	/**
	 * 根据父节点Id查询子节点树
	 * @param pId
	 * @return
	 */
	@GetMapping(value="getApiTreeByPId")
	@ResponseBody
	public MessageEntity getApiTreeByPId(@RequestParam Integer pId){
		
		MessageEntity msg = new MessageEntity();
		
		logger.info("开始查询api数信息");
		
		try{
			List<InterfaceDefinition> interfaceDefinitions = interfaceDefinitionService.findByPId(pId);
			logger.info("查询api节点树");
			msg.setSucceed(true);
			msg.setData(interfaceDefinitions);
		}catch(Exception e){
			logger.error("查询api数信息失败");
			msg.setSucceed(false);
			msg.setErrorCode(ErrorCode.common_error);
			msg.setErrorMessage("查询api数目录信息失败");
		}
		
		return msg;
	}
}
