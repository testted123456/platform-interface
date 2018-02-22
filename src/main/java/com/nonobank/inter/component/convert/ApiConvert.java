package com.nonobank.inter.component.convert;

import com.alibaba.fastjson.JSONObject;
import com.nonobank.inter.entity.InterfaceDefinition;
import com.nonobank.inter.entity.InterfaceDefinitionFront;

public class ApiConvert {
	
	public static InterfaceDefinition convertFront2Api(InterfaceDefinitionFront apiFront){
		InterfaceDefinition api = new InterfaceDefinition();
		
		if(null != apiFront){
			api.setId(apiFront.getId());
			api.setName(apiFront.getName());
			api.setDescription(apiFront.getDescription());
			api.setpId(apiFront.getpId());
			api.setSystem(apiFront.getSystem());
			api.setModule(apiFront.getModule());
			api.setBranch(apiFront.getBranch());
			api.setUrlAddress(apiFront.getUrlAddress());
			api.setApiType(apiFront.getApiType());
			api.setType(apiFront.getType());
			api.setPostWay(apiFront.getPostWay());
			
			if(apiFront.getRequestHead() != null){
				api.setRequestHead(apiFront.getRequestHead().toJSONString());
			}
			
			api.setRequestBodyType(apiFront.getRequestBodyType());
			api.setRequestBodyRowType(apiFront.getRequestBodyRowType());
			api.setRequestBody(apiFront.getRequestBody());
			
			if(apiFront.getResponseHead() != null){
				api.setResponseHead(apiFront.getResponseHead().toJSONString());
			}
			
			api.setResponseBody(apiFront.getResponseBody());
			api.setCreatedBy(apiFront.getCreatedBy());
			api.setCreatedTime(apiFront.getCreatedTime());
			api.setUpdatedBy(apiFront.getUpdatedBy());
			api.setUpdatedTime(apiFront.getUpdatedTime());
			api.setOptstatus(apiFront.getOptstatus());
		}
		
		return api;
	}
	
	public static InterfaceDefinitionFront convertApi2Front(InterfaceDefinition api){
		InterfaceDefinitionFront apiFront = new InterfaceDefinitionFront();
		
		if(null != api){
			apiFront.setId(api.getId());
			apiFront.setName(api.getName());
			apiFront.setDescription(api.getDescription());
			apiFront.setpId(api.getpId());
			apiFront.setSystem(api.getSystem());
			apiFront.setModule(api.getModule());
			apiFront.setBranch(api.getBranch());
			apiFront.setUrlAddress(api.getUrlAddress());
			apiFront.setApiType(api.getApiType());
			apiFront.setType(api.getType());
			apiFront.setPostWay(api.getPostWay());
			
			if(api.getRequestHead() != null){
				apiFront.setRequestHead(JSONObject.parseObject(api.getRequestHead()));
			}
			
			apiFront.setRequestBodyType(apiFront.getRequestBodyType());
			apiFront.setRequestBodyRowType(apiFront.getRequestBodyRowType());
			apiFront.setRequestBody(apiFront.getRequestBody());
			
			if(api.getResponseHead() != null){
				apiFront.setResponseHead(JSONObject.parseObject(api.getResponseHead()));
			}
			
			apiFront.setResponseBody(api.getResponseBody());
			apiFront.setCreatedBy(api.getCreatedBy());
			apiFront.setCreatedTime(api.getCreatedTime());
			apiFront.setUpdatedBy(api.getUpdatedBy());
			apiFront.setUpdatedTime(api.getUpdatedTime());
			apiFront.setOptstatus(api.getOptstatus());
		}
		
		return apiFront;
	}

}
