package com.nonobank.inter.component.statistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.nonobank.inter.repository.InterfaceDefinitionRepository;

import scala.collection.parallel.ParIterableLike.ResultMapping;

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
	
	public List<Map<String, String>> groupStatisDetail(){
		List<Object[]> statis = interfaceDefinitionRepository.groupStatisDetail();
		
		 /*Map<Object, Map<Boolean, Map<Object, List<Object[]>>>>  map =
		statis.stream().collect(Collectors.groupingBy(x->{return x[0];}, 
				Collectors.partitioningBy(y->{return String.valueOf(y[5]).equals("true");},
						Collectors.groupingBy(z->{return z[6];})
				)
				));*/
		
		Map<Object, Map<Object, List<Object[]>>> map = 
				statis.stream().collect(Collectors.groupingBy(x->{return x[0];}, Collectors.groupingBy(y->{return y[7];})));
		
		List<Map<String, String>> list = new ArrayList<>();
		
		map.forEach((k,v)->{
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("id", String.valueOf(k));
			resultMap.put("successSize", "0");
			resultMap.put("failSize", "0");
			
			v.forEach((x,y)->{
				
				if(y.get(0) != null){
					resultMap.put("totalSize", String.valueOf(y.get(0)[5]));
					resultMap.put("name", String.valueOf(y.get(0)[1]));
					resultMap.put("createTime", String.valueOf(y.get(0)[3]));
					resultMap.put("createdBy", String.valueOf(y.get(0)[4]));
				}
				
				Optional<Object []> optObj =
					y.stream().reduce((i,j)->{
						j[6] = Boolean.valueOf(String.valueOf(i[6])) && Boolean.valueOf(String.valueOf(j[6]));
						return j;});		
					
					if("true".equals(String.valueOf(optObj.get()[6]))){
						int size = Integer.valueOf(resultMap.get("successSize")) + 1;
						resultMap.put("successSize", String.valueOf(size));
					}else{
						int size = Integer.valueOf(resultMap.get("failSize")) + 1;
						resultMap.put("failSize", String.valueOf(size));
					}
				});
			
			int totalSize = Integer.valueOf(resultMap.get("totalSize"));
			int successSize = Integer.valueOf(resultMap.get("successSize"));
			int failSize = Integer.valueOf(resultMap.get("failSize"));
			int skipSize = totalSize - successSize - failSize;
			resultMap.put("skipSize", String.valueOf(skipSize));
			list.add(resultMap);
		});
		 
		return list;
	}
	
}
