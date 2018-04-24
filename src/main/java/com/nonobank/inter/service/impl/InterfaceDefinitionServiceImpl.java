package com.nonobank.inter.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
}
