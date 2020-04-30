package com.bchy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bchy.dao.ReadNoticeRepository;
import com.bchy.entity.ReadNotice;
import com.bchy.entity.User;

@Service
public class ReadNoticeServiceImpl implements ReadNoticeService {

	@Autowired
	private ReadNoticeRepository readNoticeRepository;

	@Override
	public ReadNotice addReadNotice(ReadNotice readNotice) {
		return readNoticeRepository.save(readNotice);
	}

	@Override
	public List<ReadNotice> getNoticeByNameId(Long userId) {
		return readNoticeRepository.getNoticeByNameId(userId);
	}

	@Override
	public void deleteByNoticeId(Long noticeId) {
		readNoticeRepository.deleteByNoticeId(noticeId);
	}

}
