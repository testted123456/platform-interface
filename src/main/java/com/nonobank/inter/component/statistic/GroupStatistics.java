package com.nonobank.inter.component.statistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONArray;
import com.nonobank.inter.repository.InterfaceDefinitionRepository;

@Component
public class GroupStatistics {

	@Autowired
	InterfaceDefinitionRepository interfaceDefinitionRepository;
	
	/**
	 * 统计group中的用例数
	 * @return
	 */
	public Map<String, List<?>> groupStatisGroupByGroup(){
		
		List<Object[]> statis = interfaceDefinitionRepository.caseStatisGroupByRef();
		List<String> data = new ArrayList<>();
		List<Integer> series = new ArrayList<>();
		
		statis.forEach(x->{
			String name = String.valueOf(x[1]);
			Object obj = x[2];
			String str = String.valueOf(obj);
			JSONArray jsonArr = JSONArray.parseArray(str);
			
			data.add(name);
			series.add(jsonArr.size());
		});
		
		Map<String, List<?>> resultMap = new HashMap<>();
		resultMap.put("data", data);
		resultMap.put("series", series);
		
		return resultMap;
	}
	
	public Map<String, List<?>> groupStatisSuccessRate(){
		List<Object[]> statis = interfaceDefinitionRepository.groupStatisSuccessRate();
		
		Map<Object, List<Object[]>> map =
		statis.stream().collect(Collectors.groupingBy(x->{return x[0];}));
		
		List<String> data = new ArrayList<>();
		List<Object> series = new ArrayList<>();
		
		map.forEach((k,v)->{
			data.add(String.valueOf(k));
			int totalSize = v.size();
			long l = v.stream().filter(x->{return "0".equals(x[0]);}).count();
			
			if(totalSize != 0){
				series.add(l/totalSize);
			}else{
				series.add(0);
			}
		});
		
		Map<String, List<?>> resultMap = new HashMap<>();
		resultMap.put("data", data);
		resultMap.put("series", series);
		
		return resultMap;
	}
	
}
