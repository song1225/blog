package com.bchy.service;

import com.bchy.entity.ThumbsUp;

public interface ThumbsUpService {
	
	//根据id获取
	ThumbsUp getThumbsUpById(Long id);
	
	//删除点赞
	void removeThumbsUp(Long id);
    
	//统计点赞量
	int getAllCount();
}
