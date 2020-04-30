package com.bchy.service;

import java.util.List;

import com.bchy.entity.Gallery;

public interface GalleryService {

	Gallery save(Gallery gallery);

	List<Gallery> findAll();

	List<Gallery> findAllByUser(Long id);

	void deleteById(long id);

}
