package com.bchy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bchy.entity.Comment;

public interface CommentService {
	//获取评论列表
	List<Comment> listCommentsByBlogId(Long blogId);
	// 添加评论内容
	Comment addComment(Comment comment);
	//统计评论量
	int getAllCount();
	
	Page<Comment> listComment(Pageable pageable);
	
	int deleteCommentById(Long commentId);
}
