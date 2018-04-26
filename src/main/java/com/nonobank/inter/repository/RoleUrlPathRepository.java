package com.nonobank.inter.repository;

import com.nonobank.inter.entity.RoleUrlPath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by tangrubei on 2018/4/11.
 */
public interface RoleUrlPathRepository extends JpaRepository<RoleUrlPath, Integer> {
    List<RoleUrlPath> findBySystemEqualsAndOptstatusNot(String system, Short optstatus);
}
