package com.bchy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bchy.entity.Notice;

public interface NoticeService {
	Notice addNotice(Notice notice);

	Page<Notice> list(Pageable pageable);
	
	List<Notice> getAllNotices();

	Notice findById(Long id);

	List<Notice> getNoticeByNameId(Long id);

	int deleteNoticeById(Long noticeId);

	Notice selectById(Long noticeId);
}
