package com.bchy.service;

import java.util.List;

import com.bchy.entity.Collection;

public interface CollectionService {
 
	//添加收藏
	Collection addCollection(Long blogId, Long userId);
	
    //取消收藏
	void deleteCollection(Long blogId, Long id);
	
	//获取所有收藏列表
	List<Collection> getAllCollections(Long userId);
    
	//通过用户ID和博客ID判断是否已经收藏
	Collection getCollectionByUserAndBlog(long userId, long blogId);

	//统计收藏量
	int getAllCount();

	
}
