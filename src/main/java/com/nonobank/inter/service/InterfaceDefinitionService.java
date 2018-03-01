package com.nonobank.inter.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.nonobank.inter.entity.InterfaceDefinition;

@Service
public interface InterfaceDefinitionService {
	
	InterfaceDefinition findById(Integer id);
	
	List<InterfaceDefinition> findByPId(Integer pId);
	
	List<InterfaceDefinition> findByIdAndBranch(Integer id, String branch);
	
	InterfaceDefinition add(InterfaceDefinition interfaceDefinition);
	
	InterfaceDefinition update(InterfaceDefinition interfaceDefinition);
}
