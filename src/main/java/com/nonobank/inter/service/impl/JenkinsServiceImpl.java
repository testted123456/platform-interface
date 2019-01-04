package com.nonobank.inter.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.nonobank.inter.service.JenkinsService;
import com.nonobank.inter.util.RedisUtil;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class JenkinsServiceImpl implements JenkinsService {
	
	public static Logger logger = LoggerFactory.getLogger(JenkinsServiceImpl.class);
	
	@Value("${jenkinsServer}")
	String jenkinsServerString;
	
	@Value("${jenkinsUser}")
	String jenkinsUser;
	
	@Value("${jenkinsPassword}")
	String jenkinsPassword;
	
	@Autowired
	RedisUtil redisUtil;
	
	JenkinsServer jServer;
	
	@Override
	public void initJenkinsServer(){
		try {
			jServer = new JenkinsServer(new URI(jenkinsServerString), jenkinsUser, jenkinsPassword);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@Override
	/*public JenkinsServer getJenkinsServer() {
		// TODO Auto-generated method stub
		
		if(jServer == null || !jServer.isRunning()){
			try {
				jServer = new JenkinsServer(new URI("http://192.168.1.122:8081/jenkins"), "admin", "123456");
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return jServer;
	}*/

//	@Override
	public List<Map<String, String>> getPackageResult() {
		// TODO Auto-generated method stub
		initJenkinsServer();
		
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		
		List<String> jenkinsJobsName = redisUtil.listGetAll("jenkinsJobsName");
		
		if(null != jenkinsJobsName && jenkinsJobsName.size()>0){
		    logger.info("从redis中获取jenkins信息");
			
		    jenkinsJobsName.forEach(x->{
				Map<String, String> jenkinsJobMap = redisUtil.getHashMap(x);
				
				if(null != jenkinsJobMap && jenkinsJobMap.size()>0){
					resultList.add(jenkinsJobMap);
				}
			});
			
			return resultList;
		}
		
		try {
			logger.info("从jenkins服务器获取信息...");
			Map<String, Job> jobs = jServer.getJobs();
			
		    jobs.forEach((k,v)->{
		    	Map<String, String> map = new HashMap<>();
		    	String name = v.getName();

		    	if(name.startsWith("dev") || name.startsWith("test")){
		    		
		    		jenkinsJobsName.add(name);
		    		
		    		if(name.startsWith("dev")){
		    			map.put("env", "dev");
		    		}else{
		    			map.put("env", "test");
		    		}
		    		
		    		map.put("name", name);
			    	
			    	try {
			    		BuildWithDetails buildWithDetails = v.details().getLastBuild().details();
						String result = buildWithDetails.getResult().name();
						map.put("result", result);
						
						long timeStamp = buildWithDetails.getTimestamp();
						Date date = new Date(timeStamp);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						map.put("time", sdf.format(date));
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	
			    	resultList.add(map);
			    	
			    	//jenkins信息保存到redis
			    	redisUtil.setHashMap(name, map, 600);
		    	}
		    });
		    
		    //jenkins信息保存到redis
		    redisUtil.listLeftSetAll("jenkinsJobsName", jenkinsJobsName, 600);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultList;
	}
	
//	public static void main(String [] args){
//		JenkinsServiceImpl jenkinsServiceImpl = new JenkinsServiceImpl();
////		
////		
//		jenkinsServiceImpl.getJenkinsServer();
//		List<Map<String, String>> list = jenkinsServiceImpl.getPackageResult();
////		
////		for(Map<String, String> map : list){
////			map.forEach((k,v)->{
////				System.out.print(k + ":" + v);
////			});
////		}
//		
//		long timestamp = System.currentTimeMillis();
//		Date date = new Date(timestamp);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(date));
//	}

}
