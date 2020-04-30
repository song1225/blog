package com.bchy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bchy.entity.ReadNotice;
import com.bchy.entity.User;

public interface ReadNoticeRepository extends JpaRepository<ReadNotice, Long>{
    @Query(value="select r from t_read r where r.user_id=?1",nativeQuery=true)
	List<ReadNotice> getNoticeByNameId(Long userId);
   
    @Transactional
    @Modifying
    @Query(value="delete  from t_read  where notice_id=?1",nativeQuery=true)
	void deleteByNoticeId(Long noticeId);
}
