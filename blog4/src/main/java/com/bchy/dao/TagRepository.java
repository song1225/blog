package com.bchy.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bchy.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{
    @Query("select t from Tag t")
	List<Tag> findTop(Pageable pageable);
}
