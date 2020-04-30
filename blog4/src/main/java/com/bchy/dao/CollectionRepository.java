package com.bchy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bchy.entity.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
	@Query("select c from Collection c where c.user.id=?1 and c.blog.id=?2")
	Collection findCollectionByUserAndBlog(long userId, long blogId);

	@Query("select c from Collection c where c.user.id=?1")
	List<Collection> findAllCollections(Long userId);
   
	@Query("select count(id) from Collection")
	int getAllCount();
}
