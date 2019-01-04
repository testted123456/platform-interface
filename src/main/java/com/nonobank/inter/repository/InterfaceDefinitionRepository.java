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
    
    List<InterfaceDefinition> findByNameLikeAndUrlAddressLikeAndBranchLikeAndModuleLikeAndSystemLikeAndOptstatusEqualsAndTypeTrue(String name, String urlAddress,
    		String branch, String module, String system, short optstatus);
    
	@Query("select idf from InterfaceDefinition idf where idf.name like %?1% and idf.urlAddress like %?2% and idf.branch like %?3% and idf.module like %?4%"
			+ " and idf.requestBody like %?6%"
			+ " and idf.system like %?5% and idf.type=1 and idf.optstatus!=2")
    List<InterfaceDefinition> searchApi(String name, String urlAddress, String branch, String module, String system, String app);

    InterfaceDefinition findBySystemAndModuleAndUrlAddressAndBranchAndOptstatus(String system, String module, String urlAddress, String branch, short optstatus);

    @Query(value="select system, count(*) as count from interface_definition  where type=1 and optstatus!=2 group by system", nativeQuery = true)
    List<Object[]> apiStatisGroupBySys();
    
    @Query(value="select module, count(*) as count from interface_definition  where type=1 and optstatus!=2 and system=:system group by module", nativeQuery = true)
    List<Object[]> apiStatisGroupByModule(@Param("system") String system);
    
    @Query(value="select branch, count(*) as count from interface_definition  where type=1 and optstatus!=2 and system=:system and module=:module group by branch", nativeQuery = true)
    List<Object[]> apiStatisGroupBySystemAndModule(@Param("system") String system, @Param("module") String module);
    
    /*@Query(value="select system, ref, count(*) from (" +
    		"select system, module, url_address, case when interface_id is null then 0 else 1 end as ref from" + 
    		"(select id, system, branch, module, url_address from interface_definition where optstatus!=2 and type=1) idf left join" +
    		"(select distinct interface_id from test_case_interface where optstatus!=2) tci on idf.id=tci.interface_id" +
    		") t group by system, ref", nativeQuery = true)*/
    @Query(value="select t1.system,t1.c1 as total, t2.c2 as ref, t1.c1-t2.c2 as unref from " +
    		"(select system, count(*) as c1 from interface_definition  where type=1 and optstatus!=2 group by system) t1 " +
    		"left join " +
    		"(select system, count(*) as c2 from  " +
    		"(select distinct system,  interface_id from test_case_interface where optstatus!=2) t3 group by system " +
    		") t2 " +
    		"on t1.system=t2.system", nativeQuery = true)
    List<Object[]> apiStatisGroupBySystemRef();
    
    @Query(value="select module, ref, count(*) from (" +
    		"select module, url_address, case when interface_id is null then 0 else 1 end as ref from" + 
    		"(select id, branch, module, url_address from interface_definition where optstatus!=2 and type=1 and system=:system) idf left join" +
    		"(select distinct interface_id from test_case_interface where optstatus!=2) tci on idf.id=tci.interface_id" +
    		") t group by module, ref", nativeQuery = true)
    List<Object[]> apiStatisGroupBySystemAndModuleRef(@Param("system")String system);
    
    @Query(value="select system, count(*) from test_case where optstatus!=2  and type=1 group by system", nativeQuery = true)
    List<Object[]> caseStatisGroupBySystem();
    
    @Query(value="select id, name, cases from test_group where optstatus!=2 and type=1 and cases is not null", nativeQuery = true)
    List<Object[]> caseStatisGroupByRef();
    
    @Query(value="select count(1) from test_case where optstatus!=2 and type=1", nativeQuery = true)
    List<Object> caseStatisTotalCount();
    
    /*@Query(value="select t.name, rd.result from " +
    	"(select tg.name, max(rh.id) as historyId from test_group tg inner join result_history rh on tg.id=rh.group_id " + 
    	"group by tg.name)t inner join result_detail rd on t.historyId=history_id", nativeQuery = true)*/
    @Query(value="select t1.historyId, tg.name, rh1.total_size, rd.tc_id,  rd.result from test_group tg, " + 
    "(select rh.group_id, max(rh.id) as historyId from result_history rh where group_id is not null group by rh.group_id) t1, result_history rh1, " + 
    "result_detail rd where tg.id=t1.group_id and t1.historyId=rh1.id and rh1.id=rd.history_id and tg.optstatus!=2 and rh1.group_id is not null", nativeQuery = true)
    List<Object[]> groupStatisSuccessRate();
    
    //查询group执行情况统计
    @Query(value="select tmp1.id, tmp1.name, tmp1.history_id, tmp1.create_time, tmp1.created_by, rh.total_size, rd.result, rd.tc_id from " +
		"(select id, name, created_by, max(history_id) history_id, max(created_time) create_time from " +
		"(select tg.id, tg.name, tg.created_by, rh.id history_id, rh.created_time, rh.tc_ids, rh.total_size " +
		"from test_group tg, result_history rh where tg.id=rh.group_id and tg.optstatus!=2)tmp group by id, name " +
		") tmp1, result_history rh, result_detail rd " +
		"where tmp1.history_id = rh.id and tmp1.history_id = rd.history_id", nativeQuery = true)
    List<Object[]> groupStatisDetail();

}
