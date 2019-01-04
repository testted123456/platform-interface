package com.nonobank.inter.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonobank.inter.component.exception.ApiException;
import com.nonobank.inter.component.result.ResultCode;
import com.nonobank.inter.entity.InterfaceDefinition;
import com.nonobank.inter.repository.InterfaceDefinitionRepository;
import com.nonobank.inter.service.InterfaceDefinitionService;
import com.nonobank.inter.service.RemoteTestCaseService;

@Service
public class InterfaceDefinitionServiceImpl implements InterfaceDefinitionService {
	
	public static Logger logger = LoggerFactory.getLogger(InterfaceDefinitionServiceImpl.class);

	@Autowired
	InterfaceDefinitionRepository interfaceDefinitionRepository;
	
	@Autowired
	RemoteTestCaseService remoteTestCaseService;

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
	public boolean delApi(String userName, Integer id){
		InterfaceDefinition api = interfaceDefinitionRepository.findByIdAndOptstatusEquals(id, (short)0);
		
		boolean isApiUsed = remoteTestCaseService.getByApiId(id);
		
		if(true == isApiUsed){//api被case引用
			logger.info("api已被case引用，api id:{}", id);
			return false;
		}
		
		api.setUpdatedTime(LocalDateTime.now(ZoneId.of("Asia/Shanghai")));
		api.setOptstatus((short)2);
		api.setUpdatedBy(userName);
		interfaceDefinitionRepository.save(api);
		
		return true;
	}

	@Override
	@Transactional
	public boolean delApiDir(String userName, Integer id) {
		InterfaceDefinition api = interfaceDefinitionRepository.findByIdAndOptstatusEquals(id, (short)0);
		
		if(null == api){
			return true;
		}
		
		api.setUpdatedTime(LocalDateTime.now(ZoneId.of("Asia/Shanghai")));
		api.setOptstatus((short)2);
		api.setUpdatedBy(userName);
		interfaceDefinitionRepository.save(api);
		
		List<InterfaceDefinition> apis = interfaceDefinitionRepository.findByPIdAndOptstatusEquals(id, (short)0);
		
		for(InterfaceDefinition inter : apis){
			if(inter.getType() == true){
				boolean result = delApi(userName, inter.getId());
				
				if(false == result){
					throw new ApiException(ResultCode.VALIDATION_ERROR.getCode(), "api id:" + inter.getId() + "已被case引用！");
				}
			}else{
				boolean result = delApiDir(userName, inter.getId());
				
				if(false == result){ 
					throw new ApiException(ResultCode.VALIDATION_ERROR.getCode(), "api id:" + inter.getId() + "已被case引用！");
				}
			}
		}
		
		return true;
		
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
	public List<InterfaceDefinition> serarchApi(String name, String urlAddress, String branch, String module, String system, String app) {
		List<InterfaceDefinition> apis =  
				interfaceDefinitionRepository.searchApi(name, urlAddress, branch, module, system, app);
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
