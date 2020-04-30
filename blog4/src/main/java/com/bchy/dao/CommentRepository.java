package com.bchy.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bchy.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

	List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
   
	@Query("select count(id) from Comment")
	int getAllCount();

	@Modifying
	@Transactional
    @Query("delete from Comment where id=?1")
	int deleteById(Long commentId);
}
