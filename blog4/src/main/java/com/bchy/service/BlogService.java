package com.bchy.service;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bchy.entity.Blog;

public interface BlogService {
	//单条查询
	Blog getBlog(Long id);

	//条件分页查询
	Page<Blog> searchBlog(Pageable pageable,Blog blog);

	//全部分页查询
	Page<Blog> listBlog(Pageable pageable);

	//新增
	Blog addBlog(Blog blog);

	//修改,先查询再赋值保存
	Blog updateBlog(Long id,Blog blog);

	//删除
	void deleteBlog(Long id);

	//手机端获取所有博客信息
	List<Blog> getAll();

	boolean updateById(Long id,Integer thumbsUp);
	
	//最新博客推荐列表
	List<Blog> listBlogTop(Integer size);
	
    //搜索获取结果
	Page<Blog> searchList(Pageable pageable, String search);
	
	//blog中文章内容转换为html
	Blog getBlogConvertHtml(Long id);
    
	//通过Type进行博客分类
	Page<Blog> listBlogByTypeId(Pageable pageable, Long id);
    
	//通过Tag进行博客分类
	Page<Blog> listBlogByTagId(Pageable pageable, Long id);
    
	//获取按照年份进行归档的信息
	Map<String,List<Blog>> archiveBlog();
	
    //获取博客总记录数
	long getBlogCount();
	
	//点赞
	Blog addThumbsUp(Long id);
	
	//取消点赞
	void removeThumbsUp(Long blogId,Long thumbsUpId);

	//获取用户自己发布的博客列表
	List<Blog> getBlogByUserId(Long id);
    
	//获取用户保存的草稿博客
	List<Blog> getDraftBlogByUserId(Long id);

	int getAllCount();

	int sumViews();
}
