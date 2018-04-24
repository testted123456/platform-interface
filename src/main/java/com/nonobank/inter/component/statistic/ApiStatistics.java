package com.nonobank.inter.component.statistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.nonobank.inter.repository.InterfaceDefinitionRepository;

@Component
public class ApiStatistics {

	@Autowired
	InterfaceDefinitionRepository interfaceDefinitionRepository;
	
	public Map<String, List<?>> statisApi(List<Object []> statis){
		
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
	
	public Map<String, List<?>> statisApiGroupBySystem(){
		List<Object []> statis = interfaceDefinitionRepository.ApiStatisGroupBySys();
		return statisApi(statis);
	}
	
	public Map<String, List<?>> statisApiGroupByModule(String system){
		List<Object []> statis = interfaceDefinitionRepository.ApiStatisGroupByModule(system);
		return statisApi(statis);
	}
	
	public Map<String, List<?>> ApiStatisGroupByModuleAndBranch(String system){
		List<Object []> statis = interfaceDefinitionRepository.ApiStatisGroupByModuleAndBranch(system);

		statis.forEach(x->{
			String module = String.valueOf(x[0]);
			String branch = String.valueOf(x[1]);
			Integer count = Integer.valueOf(x[3].toString());
		});
		
		List<Object> branches =
		statis.stream().map(x->{return x[1];}).distinct().collect(Collectors.toList());
		
		Map<Object, Map<Object, List<Object[]>>> map =
		statis.stream().collect(Collectors.groupingBy(obj->{return obj[0];},Collectors.groupingBy(obj->{return obj[0];})));
		return null;
	}
}
