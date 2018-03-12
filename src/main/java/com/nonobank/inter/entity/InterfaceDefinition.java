package com.nonobank.inter.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class InterfaceDefinition {
	
	@Id
	@GeneratedValue
	Integer id;
	
	@Column(nullable=false, columnDefinition="varchar(300) COMMENT '接口名称'")
	String name;
	
	String description;
	
	@Column(nullable=false, columnDefinition="bigint(20) COMMENT '接口父节点'")
	Integer pId;
	
	String system;
	
	String module;
	
	String branch;
	
	String urlAddress;
	
	@Column(columnDefinition="char(1) COMMENT '0:Http;1:Https;2:MQ'")
	Character apiType;
	
	@Column(nullable=true, columnDefinition="bit(1) COMMENT '0:目录，1:api'")
	Boolean type;
	
	@Column(columnDefinition="char(1) COMMENT '0:get，1:post'")
	Character postWay;
	
	@Column(columnDefinition=" varchar(255)")
	String requestHead;
	
	@Column(columnDefinition="char(1) COMMENT '0:form-data;1:x-www-from-urlencoded;2:binary'")
	Character requestBodyType;
	
	@Column(columnDefinition="char(1) COMMENT '0:Text;1:Text(text/plain);2:Json(application/json);3:Javascript(application/javascript);4:XML(application/xml);5:XML(text/xml);6:HTML(text/html)'")
	Character requestBodyRowType;
	
	@Column(columnDefinition=" varchar(2048)")
	String requestBody;
	
	@Column(columnDefinition=" varchar(255)")
	String responseHead;
	
	@Column(columnDefinition="char(1) COMMENT '0:josn;1:text;2:html;3:xml'")
	Character responseBodyType;
	
	@Column(columnDefinition=" varchar(2048)")
	String responseBody;
	
	String createdBy;
	
	@Column(columnDefinition="datetime")
	LocalDateTime createdTime;
	
	String updatedBy;
	
	@Column(columnDefinition=" datetime")
	LocalDateTime updatedTime;
	
	@Column(nullable=false, columnDefinition="smallint(1) COMMENT '0:正常，1:已更新，2:已删除'")
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

	public String getRequestHead() {
		return requestHead;
	}

	public void setRequestHead(String requestHead) {
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

	public String getResponseHead() {
		return responseHead;
	}

	public void setResponseHead(String responseHead) {
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

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
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
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
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
