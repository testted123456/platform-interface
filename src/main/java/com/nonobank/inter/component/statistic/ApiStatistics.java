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
	
	public Map<String, List<?>> statisApiRef(List<Object []> statis){
		List<Object> data = new ArrayList<>();
		List<Object> refs = new ArrayList<>();
		List<Object> unRefs = new ArrayList<>();
		
		for(Object [] args : statis){
			if(null == args[0]){//系统
				data.add("未知");
			}else{
				data.add(args[0]);
			}
			
			if(null == args[2]){//ref，被引用的api数
				refs.add(0);
			}else{
				refs.add(args[2]);
			}
			
			if(null == args[3]){//unRefs,未被引用的api数
				unRefs.add(args[1]);
			}else{
				unRefs.add(args[3]);
			}
		}
		/*
		Map<Object, List<Object[]>> map =
		statis.stream().collect(Collectors.groupingBy(x->{
			return x[0];
		}));
		
		map.forEach((k,v)->{
			if(null == k){
				data.add("未知");
			}else{
				data.add(k);
			}
			
			if(v.size() == 1){
				if(v.get(0)[1].equals("0")){
					unRefs.add(v.get(0)[2]);
					refs.add("0");
				}else{
					refs.add(v.get(0)[2]);
					unRefs.add("0");
				}
			}else{
				if(v.get(0)[1].equals("0")){
					unRefs.add(v.get(0)[2]);
					refs.add(v.get(1)[2]);
				}else{
					refs.add(v.get(0)[2]);
					unRefs.add((v.get(1)[2]));
				}
			}
		});*/
		
		Map<String, List<?>> result = new HashMap<>();
		result.put("data", data);
		result.put("unRefs", unRefs);
		result.put("refs", refs);
		return result;
	}
	
	public Map<String, List<?>> statisApiGroupBySystem(){
		List<Object []> statis = interfaceDefinitionRepository.apiStatisGroupBySys();
		return statisApi(statis);
	}
	
	public Map<String, List<?>> statisApiGroupByModule(String system){
		List<Object []> statis = interfaceDefinitionRepository.apiStatisGroupByModule(system);
		return statisApi(statis);
	}
	
	public Map<String, List<?>> ApiStatisGroupBySystemAndModule(String system, String module){
		List<Object []> statis = interfaceDefinitionRepository.apiStatisGroupBySystemAndModule(system, module);
		return statisApi(statis);
	}
	
	public Map<String, List<?>> ApiStatisGroupBySystemRef(){
		List<Object []> statis = interfaceDefinitionRepository.apiStatisGroupBySystemRef();
		return statisApiRef(statis);
	}
	
	public Map<String, List<?>> ApiStatisGroupBySystemAndModuleRef(String system){
		List<Object []> statis = interfaceDefinitionRepository.apiStatisGroupBySystemAndModuleRef(system);
		return statisApiRef(statis);
	}
}
