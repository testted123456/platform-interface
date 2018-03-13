package com.nonobank.inter.repository;

import com.nonobank.inter.entity.InterfaceDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterfaceDefinitionRepository extends JpaRepository<InterfaceDefinition, Integer> {

    InterfaceDefinition findByIdAndOptstatusEquals(Integer id, short optstatus);

    InterfaceDefinition findByNameAndSystemAndBranchAndModuleAndOptstatusEquals(String name, String system, String branch, String module, short optstatus);


    InterfaceDefinition findByNameAndPIdAndOptstatusEquals(String system,Integer pid, short optstatus);



    List<InterfaceDefinition> findByPIdAndOptstatusEquals(Integer pId, short optstatus);

    List<InterfaceDefinition> findByIdAndBranchAndOptstatusEquals(Integer id, String branch, short optstatus);

    List<InterfaceDefinition> findBySystemAndModuleAndUrlAddressAndOptstatus(String system, String module, String urlAddress, short optstatus);


}
