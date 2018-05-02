package com.nonobank.inter.component.statistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
	
	/**
	 * 统计每个系统的用例数
	 * @return
	 */
	public Map<String, List<?>> caseStatisGroupBySystem(){
		List<Object []> statis = interfaceDefinitionRepository.caseStatisGroupBySystem();
		return statisCase(statis);
	}
	
	/**
	 * 统计被group引用、未被引用的用例数
	 * @return
	 */
	public Map<String, List<?>> caseStatisGroupByRef(){
		List<Object[]> statis = interfaceDefinitionRepository.caseStatisGroupByRef();
		Map<Integer, Integer> map = new HashMap<>();
		
		statis.forEach(x->{
			Object obj = x[2];
			String str = String.valueOf(obj);
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
		
		Map<String, Integer> statisMap = 
				new HashMap<String, Integer>();
	
				/**
		new TreeMap<>(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				String pattern = "(\\d+)\\D";
				Pattern p = Pattern.compile(pattern);
				Matcher m = p.matcher(o1);
				
				int i1 = 0;
				if(m.find()){
					String s1 = m.group(1);
					i1 = Integer.parseInt(s1);
				}
				
				m = p.matcher(o1);
				
				int i2 = 0;
				if(m.find()){
					String s2 = m.group(1);
					i2 = Integer.parseInt(s2);
				}	
				
				if(i1>i2){
					return 1;
				}else if(i1<i2){
					return -1;
				}else{
					return 0;
				}
			}
		});
		**/
		
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
