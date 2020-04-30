package com.bchy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bchy.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query(value="select * from t_notice n where n.id not in (select r.notice_id from t_read r where r.user_id=?1);",nativeQuery=true )
	List<Notice> getNoReadByUserId(Long id);
    
    @Modifying
	@Transactional
    @Query("delete from Notice where id=?1")
	int deleteById(Long noticeId);

}
