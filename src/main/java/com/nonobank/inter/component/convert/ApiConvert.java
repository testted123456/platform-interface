package com.nonobank.inter.component.convert;

import com.alibaba.fastjson.JSONArray;
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
			api.setResponseBodyType(apiFront.getResponseBodyType());
//			api.setCreatedBy(apiFront.getCreatedBy());
//			api.setCreatedTime(apiFront.getCreatedTime());
//			api.setUpdatedBy(apiFront.getUpdatedBy());
//			api.setUpdatedTime(apiFront.getUpdatedTime());
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
				apiFront.setRequestHead(JSONArray.parseArray(api.getRequestHead()));
			}
			
			apiFront.setRequestBodyType(api.getRequestBodyType());
			apiFront.setRequestBodyRowType(api.getRequestBodyRowType());
			apiFront.setRequestBody(api.getRequestBody());
			
			if(api.getResponseHead() != null){
				apiFront.setResponseHead(JSONArray.parseArray(api.getResponseHead()));
			}
			
			apiFront.setResponseBody(api.getResponseBody());
			apiFront.setResponseBodyType(api.getResponseBodyType());
			apiFront.setCreatedBy(api.getCreatedBy());
			
			if(null != api.getCreatedTime()){
//				apiFront.setCreatedTime(api.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
			}
			
			apiFront.setUpdatedBy(api.getUpdatedBy());
//			apiFront.setUpdatedTime(api.getUpdatedTime());
			apiFront.setOptstatus(api.getOptstatus());
		}
		
		return apiFront;
	}

}
