package com.bchy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bchy.dao.LogRepository;
import com.bchy.entity.Log;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogRepository logRepository;

	@Override
	public Log addLog(Log log) {
		return logRepository.save(log);
	}

	@Override
	public Page<Log> list(Pageable pageable) {
		return logRepository.findAll(pageable);
	}

	@Override
	public int deleteLogById(Long logId) {
		return logRepository.deleteById(logId);
	}

}
