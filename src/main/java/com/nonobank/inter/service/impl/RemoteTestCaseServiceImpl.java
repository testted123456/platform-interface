package com.nonobank.inter.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nonobank.inter.component.exception.ApiException;
import com.nonobank.inter.component.result.Result;
import com.nonobank.inter.component.result.ResultCode;
import com.nonobank.inter.remotecontroller.RemoteTestCase;
import com.nonobank.inter.service.RemoteTestCaseService;

@Service
public class RemoteTestCaseServiceImpl implements RemoteTestCaseService {
	
	public static Logger logger = LoggerFactory.getLogger(RemoteTestCaseServiceImpl.class);
	
	@Autowired
	RemoteTestCase remoteTestCase;

	@Override
	public boolean updateSystemBrach(JSONObject jsonObj) {
		// TODO Auto-generated method stub
		Result result = remoteTestCase.updateBySystemAndBranch(jsonObj);
		
		if(result.getCode() == 10000){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean noticeSyncResult(Integer result, String system, String branch, String version) {
		// TODO Auto-generated method stub
		Result res = remoteTestCase.noticeSyncResult(String.valueOf(result), system, branch, version);

		if(res.getCode() == 10000){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean setCodeChecked(String system, String branch, String codeChecked) {
		// TODO Auto-generated method stub
		Result result = remoteTestCase.setCodeChecked(system, branch, codeChecked);

		if(result.getCode() == 10000){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public JSONObject getSysCfg(String system) {
		// TODO Auto-generated method stub
		Result result = remoteTestCase.getSysCfg(system);
		
		if(result.getCode() == 10000){
			Object obj = result.getData();
			String str = JSON.toJSONString(obj);
			logger.info("str:{}", str);
			return JSONObject.parseObject(str);
		}else{
			logger.error("查询sysCfg失败，" + result.getMsg());
			throw new ApiException(ResultCode.VALIDATION_ERROR.getCode(), "获取sysCfg信息失败");
		}
	}

	@Override
	public boolean getByApiId(Integer apiId) {
		// TODO Auto-generated method stub
		Result result = remoteTestCase.getByApiId(apiId);
		
		if(result.getCode() == 10000 && result.getData() != null){
			return true;
		}else{
			return false;
		}
	}

}
