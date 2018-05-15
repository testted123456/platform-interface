package com.nonobank.inter.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nonobank.inter.entity.InterfaceDefinition;
import com.nonobank.inter.repository.InterfaceDefinitionRepository;
import com.nonobank.inter.service.InterfaceDefinitionService;

@Service
public class InterfaceDefinitionServiceImpl implements InterfaceDefinitionService {

	@Autowired
	InterfaceDefinitionRepository interfaceDefinitionRepository;

	@Override
	public List<InterfaceDefinition> findByPId(Integer pId){
		return interfaceDefinitionRepository.findByPIdAndOptstatusEquals(pId, (short)0);
	}
	
	@Override
	public InterfaceDefinition findById(Integer id) {
		return interfaceDefinitionRepository.findByIdAndOptstatusEquals(id, (short)0);
	}
	
	@Override
	public InterfaceDefinition add(InterfaceDefinition interfaceDefinition) {
		 return interfaceDefinitionRepository.save(interfaceDefinition);
	}
	
	@Override
	public InterfaceDefinition update(InterfaceDefinition interfaceDefinition) {
		return interfaceDefinitionRepository.save(interfaceDefinition);
	}

	@Override
	public List<InterfaceDefinition> findByIdAndBranch(Integer id, String branch) {
		return interfaceDefinitionRepository.findByIdAndBranchAndOptstatusEquals(id, branch, (short)0);
	}

	@Override
	@Transactional
	public void delApiDir(String userName, Integer id) {
		InterfaceDefinition api = interfaceDefinitionRepository.findByIdAndOptstatusEquals(id, (short)0);
		List<InterfaceDefinition> apis = interfaceDefinitionRepository.findByPIdAndOptstatusEquals(id, (short)0);
		
		api.setUpdatedTime(LocalDateTime.now());
		api.setOptstatus((short)2);
		api.setUpdatedBy(userName);
		interfaceDefinitionRepository.save(api);
		
		for(InterfaceDefinition inter : apis){
			
			if(inter.getType() == true){
				inter.setOptstatus((short)2);
				inter.setUpdatedTime(LocalDateTime.now());
				inter.setUpdatedBy(userName);
				interfaceDefinitionRepository.save(inter);
			}else{
				delApiDir(userName, inter.getId());
			}
		}
	}

	@Override
	public InterfaceDefinition findBySystemAndModuleAndUrlAddress(String system, String module, String urlAddress, String branch) {
		return interfaceDefinitionRepository.findBySystemAndModuleAndUrlAddressAndBranchAndOptstatus(system, module, urlAddress, branch, (short)0);
	}

	@Override
	public List<InterfaceDefinition> findByIdIn(List<Integer> ids) {
		return interfaceDefinitionRepository.findByIdIn(ids);
	}

	@Override
	public List<InterfaceDefinition> serarchApi(String name, String urlAddress, String branch, String module, String system, Boolean type) {
		List<InterfaceDefinition> apis =  
				interfaceDefinitionRepository.searchApi(name, urlAddress, branch, module, system);
		return apis;
		/*
		Map<Integer, JSONObject> map = new HashMap<Integer, JSONObject>();
		
		apis.stream().forEach(x->{
			Integer id = x.getId();
			Integer pId = x.getpId();
			String apiName = x.getName();
			boolean apiType = x.getType();
			JSONObject api = new JSONObject();
			api.put("id", id);
			api.put("pId", pId);
			api.put("name", apiName);
			api.put("type", apiType);
			
			if("0".equals(String.valueOf(pId))){
				map.put(Integer.valueOf(String.valueOf(id)), api);
			}
			
			while(!pId.equals(0)){
				JSONObject p_api = null;
				
				if(map.containsKey(pId)){
					p_api = map.get(pId);
					JSONArray children = p_api.getJSONArray("children");
					children.add(api);
					break;
				}else{
					p_api = new JSONObject();
					InterfaceDefinition p_inter = interfaceDefinitionRepository.findByIdAndOptstatusEquals(pId, (short)0);
					
					JSONArray children = new JSONArray();
					children.add(api);
					
					id = p_inter.getId();
					pId = p_inter.getpId();
					apiName = p_inter.getName();
					apiType = p_inter.getType();
					
					p_api.put("id", id);
					p_api.put("pId", pId);
					p_api.put("name", apiName);
					p_api.put("type", apiType);
					p_api.put("children", children);
					
					map.put(id, p_api);
					api = p_api;
					
					if(pId.equals(0)){
						break;
					}
				}
			}
		});
		
		List<JSONObject> list = new ArrayList<>();
		
		map.forEach((k,v)->{
			if(v.getString("pId").equals("0")){
				list.add(v);
			}
		});
		
		return list;
		*/
	}
	
}
