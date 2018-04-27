package com.nonobank.inter.component.statistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nonobank.inter.repository.InterfaceDefinitionRepository;

@Component
public class CaseStatistics {
	
	@Autowired
	InterfaceDefinitionRepository interfaceDefinitionRepository;
	
public Map<String, List<?>> statisCase(List<Object []> statis){
		
		List<String> data = new ArrayList<>();
		List<Integer> series = new ArrayList<>();
		
		statis.forEach(x->{
			Object name = x[0];
			Object value = x[1];
			data.add(null == name || "".equals(name) ? "未知" : name.toString());
			series.add(Integer.valueOf(String.valueOf(value)));
		});
		
		Map<String, List<?>> map = new HashMap<>();
		map.put("data", data);
		map.put("series", series);
		return map;
	}
	
	public Map<String, List<?>> caseStatisGroupBySystem(){
		List<Object []> statis = interfaceDefinitionRepository.caseStatisGroupBySystem();
		return statisCase(statis);
	}
	
	public Map<String, List<?>> caseStatisGroupByRef(){
		List<Object> statis = interfaceDefinitionRepository.caseStatisGroupByRef();
		Map<Integer, Integer> map = new HashMap<>();
		
		statis.forEach(x->{
//			Object obj = x[0];
			String str = String.valueOf(x);
			System.out.println(str);
			JSONArray jsonArr = JSONArray.parseArray(str);
			jsonArr.forEach(y->{
				if(y instanceof JSONObject){
					JSONObject jsonObj = (JSONObject)y;
					Integer id = jsonObj.getInteger("id");
					if(map.containsKey(id)){
						int count = map.get(id);
						map.put(id, ++count);
					}else{
						map.put(id, 1);
					}
				}
			});
		});
		
		int totalRefCases = map.size();
		
		List<Object> totalCases =
		interfaceDefinitionRepository.caseStatisTotalCount();
		
		String totalCaseCount = String.valueOf(totalCases.get(0));
		
		List<String> data = new ArrayList<>();
		List<Integer> series = new ArrayList<>();
		
		data.add("0次");
		series.add(Integer.valueOf(totalCaseCount)-totalRefCases);
		
		Map<String, Integer> statisMap = new HashMap<String, Integer>();
		
		map.forEach((k,v)->{
			if(statisMap.containsKey(v + "次")){
				int count = statisMap.get(v + "次");
				statisMap.put(v + "次", ++count);
			}else{
				statisMap.put(v + "次", 1);
			}
		});
		
		statisMap.forEach((k,v)->{
			data.add(k);
			series.add(v);
		});
		
		Map<String, List<?>> resultMap = new HashMap<>();
		resultMap.put("data", data);
		resultMap.put("series", series);
		
		return resultMap;
	}
}
