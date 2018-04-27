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
    List<Object[]> apiStatisGroupBySys();
    
    @Query(value="select module, count(*) as count from interface_definition  where type=1 and optstatus!=2 and system=:system group by module", nativeQuery = true)
    List<Object[]> apiStatisGroupByModule(@Param("system") String system);
    
    @Query(value="select branch, count(*) as count from interface_definition  where type=1 and optstatus!=2 and system=:system and module=:module group by branch", nativeQuery = true)
    List<Object[]> apiStatisGroupBySystemAndModule(@Param("system") String system, @Param("module") String module);
    
    @Query(value="select system, ref, count(*) from (" +
    		"select system, module, url_address, case when interface_id is null then 0 else 1 end as ref from" + 
    		"(select id, system, branch, module, url_address from interface_definition where optstatus!=2 and type=1) idf left join" +
    		"(select distinct interface_id from test_case_interface where optstatus!=2) tci on idf.id=tci.interface_id" +
    		") t group by system, ref", nativeQuery = true)
    List<Object[]> apiStatisGroupBySystemRef();
    
    @Query(value="select module, ref, count(*) from (" +
    		"select module, url_address, case when interface_id is null then 0 else 1 end as ref from" + 
    		"(select id, branch, module, url_address from interface_definition where optstatus!=2 and type=1 and system=:system) idf left join" +
    		"(select distinct interface_id from test_case_interface where optstatus!=2) tci on idf.id=tci.interface_id" +
    		") t group by module, ref", nativeQuery = true)
    List<Object[]> apiStatisGroupBySystemAndModuleRef(@Param("system")String system);
    
    @Query(value="select system, count(*) from test_case where optstatus!=2  and type=1 group by system", nativeQuery = true)
    List<Object[]> caseStatisGroupBySystem();
    
    @Query(value="select cases from test_group where optstatus!=2 and type=1 and cases is not null", nativeQuery = true)
    List<Object> caseStatisGroupByRef();
    
    @Query(value="select count(1) from test_case where optstatus!=2 and type=1", nativeQuery = true)
    List<Object> caseStatisTotalCount();
}
