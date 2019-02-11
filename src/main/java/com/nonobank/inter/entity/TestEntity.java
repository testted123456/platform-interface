package com.nonobank.inter.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
public class TestEntity {
	@Id
	@GeneratedValue
	Integer id;

	@Column(nullable=false, columnDefinition="varchar(300) COMMENT '测试字段'")
	@JsonSerialize(using=MySerializer.class)
	@JsonDeserialize(using=MyDeserializer.class)
	String jsonArray;
	
//	@Column(columnDefinition="datetime")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	LocalDateTime time;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getJsonArray() {
		return jsonArray;
	}
	
	public void setJsonArray(String jsonArray) {
		this.jsonArray = jsonArray;
	}
}
