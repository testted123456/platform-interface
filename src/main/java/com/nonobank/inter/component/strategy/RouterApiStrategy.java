package com.nonobank.inter.component.strategy;

import com.alibaba.fastjson.JSONObject;

public class RouterApiStrategy implements ApiDocStrategy {

	@Override
	public Character getApiType() {
		// TODO Auto-generated method stub
		return '0';
	}

	@Override
	public Character getResponseBodyType() {
		// TODO Auto-generated method stub
		return '1';
	}

	@Override
	public String getRequestHead() {
		// TODO Auto-generated method stub
		return "[{\"Key\":\"Content-Type\",\"Value\":\"application/x-www-form-urlencoded\"}]";
	}

	@Override
	public String getResponseHead() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequest(String request) {
		// TODO Auto-generated method stub
		JSONObject subRequest = JSONObject.parseObject(request);
		
		if(!subRequest.containsKey("application")){
			subRequest.put("application", "应用名称，如ChekIdAndCard.Req");
		}
		
		subRequest.put("appUser", "终端应用者，如qtpay");
		subRequest.put("version", "客户端版本号");
		subRequest.put("osType", "手机终端类型，如Android2.2");
		subRequest.put("mobileSerialNum", "${generateRandomValue(\"40\")}");
		subRequest.put("userIP", "${getRandomIP()}");
		subRequest.put("clientType", "客户端类型");
		subRequest.put("token", "Token号，0000");
		subRequest.put("customerId", "客户号，如A000938545");
		subRequest.put("Phone", "手机号");
		subRequest.put("longitude", "121.48");
		subRequest.put("latitude", "31.23");
		subRequest.put("mobileNo", "手机号");
		subRequest.put("transDate", "${getServerTimeWithFormat(\"YYYYMMDD\")}");
		subRequest.put("transTime", "${getServerTimeWithFormat(\"hhmmss\")");
		subRequest.put("transLogNo", "流水号");
		subRequest.put("sign", "true");
		subRequest.put("appVersion", "客户端版本标志，V3版本的客户端必传递此参数。appVersion值：V3");
		JSONObject jsonOfReq = new JSONObject();
		jsonOfReq.put("requestXml", subRequest);
		return jsonOfReq.toJSONString();
	}

	@Override
	public String getResponse(String response) {
		// TODO Auto-generated method stub
		return response;
	}

}
