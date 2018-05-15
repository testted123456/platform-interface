package com.nonobank.inter.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationHome;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.nonobank.inter.component.remoteEntity.RemoteTestCase;
import com.nonobank.inter.service.CodeCheckService;
import com.nonobank.inter.service.GitService;

@Service
@EnableAsync
public class CodeCheckServiceImpl implements CodeCheckService {
	
	public static Logger logger = LoggerFactory.getLogger(CodeCheckServiceImpl.class);
	
	@Value("${codePath}")
    private String codePath;
	
	@Value("${checkReportPath}")
    private String checkReportPath;
	
	@Value("${firelineJarPath}")
	private String firelineJarPath;
	
	@Value("${JAVA_HOME}")
	private String JAVA_HOME;
	
	@Autowired
	RemoteTestCase remoteTestCase;
	
	@Autowired
	GitService gitService;

	@Override
	@Async
	public void checkCode(String system, String branch) {
		 ApplicationHome home = new ApplicationHome(this.getClass());
	        File branchCodeDir = new File(home.getDir(), String.format("%s/%s/%s", codePath, system, branch));
	        File checkReportDir = new File(home.getDir(), String.format("%s/%s/%s", checkReportPath, system, branch));

	        if(!branchCodeDir.exists()){
	        	logger.warn("{" + system +"}代码还未clone...");
	        	JSONObject jsonOfSysCfg = remoteTestCase.getSysCfg(system);
	        	String gitAddress = jsonOfSysCfg.getString("gitAddress");
	        	gitService.cloneCode(system, branch, gitAddress);
	        }
	        
	        if(!checkReportDir.exists()){
	        	logger.warn(checkReportDir.getAbsolutePath() + ":不存在！");
	        	checkReportDir.mkdirs();
	        }
	        
	        String cmd = "export JAVA_HOME=" + JAVA_HOME
	        			+ ";export PATH=$PATH:$JAVA_HOME;" +
	        		"java -jar " + firelineJarPath + 
	        		" -s=" + branchCodeDir +
	        		" -r=" + checkReportDir;
	        
	        String[] cmds = { "/bin/sh", "-c", cmd };
	        
	        try {
	        	logger.info("开始对{}进行代码检测...", system);
				Process process = Runtime.getRuntime().exec(cmds);
				BufferedReader rd = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line;
				while ((line = rd.readLine()) != null) {
					logger.info(line);
				}
				logger.info("对{}进行代码检测完成...", system);
			} catch (IOException e) {
				logger.error("对{}进行代码检测失败" + e.getLocalizedMessage(), system);
			}
	        
	        //同步codeChecked
	        remoteTestCase.setCodeChecked(system, branch, "true");
	}

}
