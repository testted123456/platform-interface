package com.nonobank.inter.entity;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import com.alibaba.fastjson.JSONArray;

public class InterfaceDefinitionFront {
	
	Integer id;
	
	@NotEmpty(message="接口名称不能为空")
	String name;
	
	String description;
	
	@NotNull(message="接口父节点不能为空")
	Integer pId;
	
	@NotEmpty(message="系统不能为空")
	String System;
	
	String module;
	
	@NotEmpty(message="接口分支不能为空")
	String branch;
	
	@NotEmpty(message="接口地址不能为空")
	String urlAddress;
	
	@NotNull(message="接口类型不能为空")
	Character apiType;
	
	@NotNull(message="接点类型不能为空")
	Boolean type;
	
	Character postWay;
	
	JSONArray requestHead;
	
	Character requestBodyType;
	
	Character requestBodyRowType;
	
	String requestBody;
	
	JSONArray responseHead;
	
	@NotNull(message="响应类型不能为空")
	Character responseBodyType;
	
	String responseBody;
	
	String createdBy;
	
	String createdTime;
	
	String updatedBy;
	
	LocalDateTime updatedTime;
	
	Short optstatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Boolean getType() {
		return type;
	}

	public void setType(Boolean type) {
		this.type = type;
	}

	public Character getApiType() {
		return apiType;
	}

	public void setApiType(Character apiType) {
		this.apiType = apiType;
	}

	public Character getPostWay() {
		return postWay;
	}

	public void setPostWay(Character postWay) {
		this.postWay = postWay;
	}

	public JSONArray getRequestHead() {
		return requestHead;
	}

	public void setRequestHead(JSONArray requestHead) {
		this.requestHead = requestHead;
	}

	public Character getRequestBodyType() {
		return requestBodyType;
	}

	public void setRequestBodyType(Character requestBodyType) {
		this.requestBodyType = requestBodyType;
	}
	
	public Character getRequestBodyRowType() {
		return requestBodyRowType;
	}

	public void setRequestBodyRowType(Character requestBodyRowType) {
		this.requestBodyRowType = requestBodyRowType;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public JSONArray getResponseHead() {
		return responseHead;
	}

	public void setResponseHead(JSONArray responseHead) {
		this.responseHead = responseHead;
	}

	public Character getResponseBodyType() {
		return responseBodyType;
	}

	public void setResponseBodyType(Character responseBodyType) {
		this.responseBodyType = responseBodyType;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getSystem() {
		return System;
	}

	public void setSystem(String system) {
		System = system;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getUrlAddress() {
		return urlAddress;
	}

	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}

	public Short getOptstatus() {
		return optstatus;
	}

	public void setOptstatus(Short optstatus) {
		this.optstatus = optstatus;
	}
}
