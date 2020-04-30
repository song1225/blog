package com.bchy.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bchy.entity.Log;

public interface LogRepository extends JpaRepository<Log, Long>{
	@Modifying
	@Transactional
    @Query("delete from Log where id=?1")
	int deleteById(Long logId);

}
