package com.bchy.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bchy.dao.NoticeRepository;
import com.bchy.entity.Notice;
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;
	@Override
	public Notice addNotice(Notice notice) {
		return noticeRepository.save(notice);
	}
	@Override
	public Page<Notice> list(Pageable pageable) {
		return noticeRepository.findAll(pageable);
	}
	@Override
	public List<Notice> getAllNotices() {
		return noticeRepository.findAll();
	}
	@Override
	public Notice findById(Long id) {
		return noticeRepository.findOne(id);
	}
	@Override
	public List<Notice> getNoticeByNameId(Long id) {
		return noticeRepository.getNoReadByUserId(id);
	}
	@Override
	public int deleteNoticeById(Long noticeId) {
		return noticeRepository.deleteById(noticeId);
	}
	@Override
	public Notice selectById(Long noticeId) {
		return noticeRepository.findOne(noticeId);
	}
}
