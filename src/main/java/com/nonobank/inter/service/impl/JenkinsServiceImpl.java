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

import redis.clients.jedis.Jedis;

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
	
	@Override
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
	
	/*public static void main(String [] args){
		Jedis jedis = new Jedis("192.168.1.121", 6379);
		List<String> list = jedis.lrange("jenkinsJobsName", 0, 50);
		
		for(String s:list){
			Map<String, String> map = jedis.hgetAll(s);
			if(map.size() > 0){
				jedis.hdel(s, "env", "name", "result", "time");
			}
			
			System.out.print(s);
		}
		
		for(String s:list){
			jedis.lrem("jenkinsJobsName", 0, s);
		}
		
	}*/

}
