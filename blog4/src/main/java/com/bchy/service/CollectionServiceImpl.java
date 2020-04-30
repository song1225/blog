package com.bchy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bchy.dao.BlogRepository;
import com.bchy.dao.CollectionRepository;
import com.bchy.dao.UserRepository;
import com.bchy.entity.Collection;

@Service
public class CollectionServiceImpl implements CollectionService {

	@Autowired
	private CollectionRepository collectionRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private UserRepository userRepository;
	@Override
	public Collection addCollection(Long blogId, Long userId) {
		Collection c=new Collection();
		c.setBlog(blogRepository.findOne(blogId));
		c.setUser(userRepository.findOne(userId));
		return collectionRepository.save(c);
	}

	@Override
	public void deleteCollection(Long blogId, Long userId) {
		Collection c=collectionRepository.findCollectionByUserAndBlog(userId, blogId);
		//删除前先把关联信息重置为空再删除
		c.setBlog(null);
		c.setUser(null);
		collectionRepository.delete(c.getId());
	}

	@Override
	public List<Collection> getAllCollections(Long userId) {
		return collectionRepository.findAllCollections(userId);
	}

	@Override
	public Collection getCollectionByUserAndBlog(long userId, long blogId) {
		return collectionRepository.findCollectionByUserAndBlog(userId,blogId);
		
	}

	@Override
	public int getAllCount() {
		return collectionRepository.getAllCount();
	}

	

}
