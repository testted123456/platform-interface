package com.nonobank.inter.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.nonobank.inter.entity.InterfaceDefinition;

public interface RoleUrlPathRepository extends JpaRepository<InterfaceDefinition, Integer> {
	
    @Query(value="select url_path, role_name from role_url_path where system='api' and optstatus!=2 ", nativeQuery = true)
    List<Object[]> findUrlAndRole();
}
