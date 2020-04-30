package com.bchy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bchy.entity.Gallery;

public interface GalleryRepository extends JpaRepository<Gallery, Long>{
    @Query(value="select * from t_gallery t where t.user_id=?1",nativeQuery=true)
	List<Gallery> findAllByUser(Long id);
	
}
