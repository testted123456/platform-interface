package com.nonobank.inter.service;

import java.util.List;
import java.util.Map;

public interface JenkinsService {
	
	void initJenkinsServer();
	
//	JenkinsServer getJenkinsServer();
	
	List<Map<String, String>> getPackageResult();
}
