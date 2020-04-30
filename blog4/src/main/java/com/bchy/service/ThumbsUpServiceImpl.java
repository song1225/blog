package com.bchy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bchy.dao.ThumbsUpRepository;
import com.bchy.entity.ThumbsUp;
@Service
public class ThumbsUpServiceImpl implements ThumbsUpService {
	
	@Autowired
	private ThumbsUpRepository thumbsUpRepository;

	@Override
	public ThumbsUp getThumbsUpById(Long id) {
		// TODO Auto-generated method stub
		return thumbsUpRepository.findOne(id);
	}

	@Override
	public void removeThumbsUp(Long id) {
		thumbsUpRepository.delete(id);
	}

	@Override
	public int getAllCount() {
		// TODO Auto-generated method stub
		return thumbsUpRepository.getAllCount();
	}

}
