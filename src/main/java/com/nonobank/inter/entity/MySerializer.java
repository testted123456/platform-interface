package com.nonobank.inter.entity;

import java.io.IOException;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MySerializer extends JsonSerializer<JSONArray> {

	@Override
	public void serialize(JSONArray value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		System.out.print("++++++++++++");
		gen.writeString(value.toJSONString());
	}

}
