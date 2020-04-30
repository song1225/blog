package com.bchy.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bchy.dao.CommentRepository;
import com.bchy.entity.Comment;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public List<Comment> listCommentsByBlogId(Long blogId) {
		Sort sort = new Sort("createTime");
		// 获取评论信息，parentCommen为null
		List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
		return eachComment(comments);
	}

	@Override
	public Comment addComment(Comment comment) {
		Long parentCommentId = comment.getParentComment().getId();
		if (parentCommentId != -1) {
			comment.setParentComment(commentRepository.findOne(parentCommentId));
		} else {
			comment.setParentComment(null);
		}
		comment.setCreateTime(new Date());
		return commentRepository.save(comment);
	}

	/**
	 * 循环每个顶级的评论节点
	 * 
	 * @param comments
	 * @return
	 */
	private List<Comment> eachComment(List<Comment> comments) {
		List<Comment> commentViews = new ArrayList<Comment>();
		for (Comment comment : comments) {
			Comment c = new Comment();
			BeanUtils.copyProperties(comment, c);
			commentViews.add(c);
		}
		// 合并评论的各层子代到第一层子代集合中
		combineChildren(commentViews);
		return commentViews;
	}

	/**
	 * root根节点，blog不为空的对象集合
	 * @param comments
	 */
	private void combineChildren(List<Comment> comments) {
		for (Comment comment : comments) {
			List<Comment> replys = comment.getReplyComments();
			for (Comment reply : replys) {
				//迭代循环，找出子代，存放在tempReplys中
				recursively(reply);
			}
			//修改顶级节点的reply集合为迭代处理后的集合
			comment.setReplyComments(tempReplys);
			//清除临时存放区
			tempReplys=new ArrayList<>();
		}

	}
	//存放迭代找出的所有子代集合
	private List<Comment> tempReplys=new ArrayList<Comment>();
	
	/**
	 * 递归迭代
	 * @param comment
	 */
	private void recursively(Comment comment){
		//顶级节点添加到临时存放集合
		tempReplys.add(comment);
		if(comment.getReplyComments().size()>0){
			List<Comment> replys=comment.getReplyComments();
			for (Comment reply : replys) {
				tempReplys.add(reply);
				if(reply.getReplyComments().size()>0){
					recursively(reply);
				}
			}
		}
	}

	@Override
	public int getAllCount() {
		return commentRepository.getAllCount();
	}

	@Override
	public Page<Comment> listComment(Pageable pageable) {
		return commentRepository.findAll(pageable);
	}

	@Override
	public int deleteCommentById(Long commentId) {
		return commentRepository.deleteById(commentId);
	}

}
