package com.nonobank.inter.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nonobank.inter.entity.InterfaceDefinition;
import com.nonobank.inter.repository.InterfaceDefinitionRepository;
import com.nonobank.inter.service.InterfaceDefinitionService;

@Service
public class InterfaceDefinitionServiceImpl implements InterfaceDefinitionService {

	@Autowired
	InterfaceDefinitionRepository interfaceDefinitionRepository;
	
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
}
