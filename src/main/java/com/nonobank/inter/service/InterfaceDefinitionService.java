package com.nonobank.inter.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.nonobank.inter.entity.InterfaceDefinition;

@Service
public interface InterfaceDefinitionService {
	
	InterfaceDefinition findById(Integer id);
	
	List<InterfaceDefinition> findByPId(Integer pId);
	
	List<InterfaceDefinition> findByIdAndBranch(Integer id, String branch);
	
	InterfaceDefinition findBySystemAndModuleAndUrlAddress(String system, String module, String urlAddress, String branch);
	
	InterfaceDefinition add(InterfaceDefinition interfaceDefinition);
	
	InterfaceDefinition update(InterfaceDefinition interfaceDefinition);
	
	boolean delApiDir(String userName, Integer id);
	
    boolean delApi(String userName, Integer id);
	
	List<InterfaceDefinition> findByIdIn(List<Integer> ids);
	 
	List<InterfaceDefinition> serarchApi(String name, String url, String branch, String module, String system, String app);

}
