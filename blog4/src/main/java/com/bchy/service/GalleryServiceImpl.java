package com.bchy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bchy.dao.GalleryRepository;
import com.bchy.entity.Gallery;

@Service
public class GalleryServiceImpl implements GalleryService {
     
	@Autowired
	private GalleryRepository galleryRepository;
	@Override
	public Gallery save(Gallery gallery) {
		return galleryRepository.save(gallery);
	}
	@Override
	public List<Gallery> findAll() {
		return galleryRepository.findAll();
	}
	@Override
	public List<Gallery> findAllByUser(Long id) {
		return galleryRepository.findAllByUser(id);
	}
	@Override
	public void deleteById(long id) {
		galleryRepository.delete(id);;
	}

}
