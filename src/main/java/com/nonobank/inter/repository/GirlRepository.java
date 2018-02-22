package com.nonobank.inter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nonobank.inter.entity.Girl;

public interface GirlRepository extends JpaRepository<Girl, Integer> {
	
//	@Query("from Girl g where g.id=:id and g.age=:age")
//	public Girl findByIdAndAge(@Param("id")Integer id, @Param("age")Integer age);
	
	@Query(value="select * from girl where id=?1 and age=?2",nativeQuery=true)
	public Girl findByIdAndAge(Integer id, Integer age);

}
