package com.nonobank.inter.repository;

import com.nonobank.inter.entity.InterfaceDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InterfaceDefinitionRepository extends JpaRepository<InterfaceDefinition, Integer> {

    InterfaceDefinition findByIdAndOptstatusEquals(Integer id, short optstatus);

    InterfaceDefinition findByNameAndSystemAndBranchAndModuleAndOptstatusEquals(String name, String system, String branch, String module, short optstatus);

    InterfaceDefinition findByNameAndPIdAndOptstatusEquals(String system,Integer pid, short optstatus);

    List<InterfaceDefinition> findByPIdAndOptstatusEquals(Integer pId, short optstatus);
    
    List<InterfaceDefinition> findByIdIn(List<Integer> ids);

    List<InterfaceDefinition> findByIdAndBranchAndOptstatusEquals(Integer id, String branch, short optstatus);

    InterfaceDefinition findBySystemAndModuleAndUrlAddressAndBranchAndOptstatus(String system, String module, String urlAddress, String branch, short optstatus);

    @Query(value="select system, count(*) as count from interface_definition  where type=1 and optstatus!=2 group by system", nativeQuery = true)
    List<Object[]> ApiStatisGroupBySys();
    
    @Query(value="select module, count(*) as count from interface_definition  where type=1 and optstatus!=2 and system=:system group by module", nativeQuery = true)
    List<Object[]> ApiStatisGroupByModule(@Param("system") String system);
    
    @Query(value="select module, branch, count(*) as count from interface_definition  where type=1 and optstatus!=2 and system=:system group by module, branch", nativeQuery = true)
    List<Object[]> ApiStatisGroupByModuleAndBranch(@Param("system") String system);
}
