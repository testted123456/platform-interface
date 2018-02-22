package com.nonobank.inter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nonobank.inter.entity.InterfaceDefinition;

@Service
public interface InterfaceDefinitionService {
	
	InterfaceDefinition findById(Integer id);
	
	Optional<InterfaceDefinition> findById(Optional<Integer> id);

	List<InterfaceDefinition> findByPId(Integer pId);
	
    Optional<List<InterfaceDefinition>> findByPId(Optional<Integer> pId);
	
	List<InterfaceDefinition> findByIdAndBranch(Integer id, String branch);
	
	InterfaceDefinition add(InterfaceDefinition interfaceDefinition);
	
	Optional<InterfaceDefinition> add(Optional<InterfaceDefinition> interfaceDefinition);
	
	InterfaceDefinition update(InterfaceDefinition interfaceDefinition);
}
