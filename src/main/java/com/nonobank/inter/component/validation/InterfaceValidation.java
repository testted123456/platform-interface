package com.nonobank.inter.component.validation;

import com.nonobank.inter.entity.InterfaceDefinition;

public class InterfaceValidation {

	public static String validate(InterfaceDefinition interfaceDefinition){
		String result = "success";
		
		if(null == interfaceDefinition){
			result = "接口不能为空.";
			return result;
		}
		
		if(null == interfaceDefinition.getName() || interfaceDefinition.getName().trim().equals("")){
			result = "接口名称不能为空.";
			return result;
		}
		
		if(null == interfaceDefinition.getUrlAddress() || interfaceDefinition.getUrlAddress().equals("")){
			result = "接口地址不能为空.";
			return result;
		}
		
		if(null == interfaceDefinition.getSystem() || interfaceDefinition.getSystem().equals("")){
			result = "系统不能为空.";
			return result;
		}
		
		if(null == interfaceDefinition.getApiType()){
			result = "接口类型不能为空.";
			return result;
		}
		
		if(!interfaceDefinition.getApiType().equals(2)){
			if(null == interfaceDefinition.getPostWay() || !(interfaceDefinition.getPostWay().equals('0') || interfaceDefinition.getPostWay().equals('1'))){
				result = "http协议提交类型有误.";
				return result;
			}
		}
		
		return result;
	}
}
