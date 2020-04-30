package com.bchy.service;

import java.util.List;

import com.bchy.entity.ReadNotice;
import com.bchy.entity.User;

public interface ReadNoticeService {
	ReadNotice addReadNotice(ReadNotice readNotice);

	List<ReadNotice> getNoticeByNameId(Long userId);

	void deleteByNoticeId(Long noticeId);
}
