package com.nonobank.inter.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nonobank.inter.entity.InterfaceDefinition;

public interface InterfaceDefinitionRepository extends JpaRepository<InterfaceDefinition, Integer> {

	InterfaceDefinition findByIdAndOptstatusEquals(Integer id, short optstatus);
	
	List<InterfaceDefinition> findByPIdAndOptstatusEquals(Integer pId, short optstatus);
	
	List<InterfaceDefinition> findByIdAndBranchAndOptstatusEquals(Integer id, String branch, short optstatus);
	
}
