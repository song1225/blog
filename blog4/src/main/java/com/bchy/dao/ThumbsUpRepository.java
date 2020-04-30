package com.bchy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bchy.entity.ThumbsUp;

public interface ThumbsUpRepository extends JpaRepository<ThumbsUp,Long> {
    @Query("select count(id) from ThumbsUp")
	int getAllCount();

}
