package com.nonobank.inter.service;

import com.alibaba.fastjson.JSONObject;

public interface RemoteTestCaseService {
	
	public boolean updateSystemBrach(JSONObject jsonObj);
	
	public boolean noticeSyncResult(Integer result, String system, String branch, String version);
	
	public boolean setCodeChecked(String system, String branch, String codeChecked);
	
	public JSONObject getSysCfg(String system);

}
