package com.nonobank.inter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nonobank.inter.entity.TestEntity;

public interface TestEntityRepository extends JpaRepository<TestEntity, Integer> {

}
