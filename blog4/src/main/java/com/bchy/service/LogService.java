package com.bchy.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bchy.entity.Log;

public interface LogService {

	Log addLog(Log log);

	Page<Log> list(Pageable pageable);

	int deleteLogById(Long logId);
 
}
