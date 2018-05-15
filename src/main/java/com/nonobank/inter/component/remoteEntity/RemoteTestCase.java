package com.nonobank.inter.component.remoteEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import com.nonobank.apps.HttpClient;
import com.nonobank.inter.component.exception.ApiException;
import com.nonobank.inter.component.result.ResultCode;

@Component
public class RemoteTestCase {
	
		public static Logger logger = LoggerFactory.getLogger(RemoteTestCase.class);


	 	@Value("${httpServer.caseServer}")
	    private String caseServer;
	 	
	 	public void noticeSyncResult(Integer result, String system, String branch, String version){
	 		Map<String, String> map = new HashMap<>();
	 		map.put("system", system);
	 		map.put("branch", branch);
	 		map.put("version", version);
	 		map.put("result", String.valueOf(result));
	 		
	 		HttpClient httpClient = new HttpClient();
			CloseableHttpClient client = httpClient.getHttpClient();
			CloseableHttpResponse response = null;
			
			try {
				response = httpClient.doGetSend(client, null, caseServer + "sysBranch/noticeSyncResult", map);
//				String resOfString = httpClient.getResBody(response);
//				JSONObject resOfJson = JSON.parseObject(resOfString);
			} catch (IOException e) {
				e.printStackTrace();
			} 
	 	}
	 	
	 	public void setCodeChecked(String system, String branch, String codeChecked){
	 		Map<String, String> map = new HashMap<>();
	 		map.put("system", system);
	 		map.put("branch", branch);
	 		map.put("codeChecked", codeChecked);
	 		HttpClient httpClient = new HttpClient();
			CloseableHttpClient client = httpClient.getHttpClient();
			CloseableHttpResponse response = null;
			
			try {
				response = httpClient.doGetSend(client, null, caseServer + "sysBranch/updateCodeChecked", map);
			} catch (IOException e) {
				e.printStackTrace();
			} 
	 	}
	 	
	 	public JSONObject getSysCfg(String system){
	 		Map<String, String> map = new HashMap<>();
	 		map.put("system", system);
	 		HttpClient httpClient = new HttpClient();
			CloseableHttpClient client = httpClient.getHttpClient();
			CloseableHttpResponse response = null;
			
			try {
				response = httpClient.doGetSend(client, null, caseServer + "sysCfg/getBySystem", map);
				String resOfString = httpClient.getResBody(response);
				JSONObject resOfJson = JSONObject.parseObject(resOfString);
				
				if(resOfJson.getInteger("code").equals(10000)){
					logger.info("查询sysCfg成功");
					JSONObject apiOfJson = resOfJson.getJSONObject("data");
					return apiOfJson;
				}else{
					logger.error("查询sysCfg失败，" + resOfJson.getString("errorMessage"));
					throw new ApiException(ResultCode.VALIDATION_ERROR.getCode(), "获取sysCfg信息失败");
				}
			} catch (HttpException e) {
				logger.error("查询sysCfg失败," + e.getLocalizedMessage());
				return null;
			} catch (IOException e) {
				logger.error("查询sysCfg失败," + e.getLocalizedMessage());
				return null;
			} 
	 	}
}
