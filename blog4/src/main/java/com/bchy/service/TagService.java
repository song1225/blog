package com.bchy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bchy.entity.Tag;

public interface TagService {
	//标签新增
	Tag addTag(Tag tag);

	//根据ID查询
	Tag getTag(Long id);

	//标签分页查询
	Page<Tag> listTag(Pageable pageable);

	//博客拥有多个标签
	List<Tag> listTag(String ids);

	//标签修改
	Tag updateTag(Long id,Tag tag);

	//删除标签
	void deleteTag(Long id);

	List<Tag> allTag();
	
	//查询标签Top6显示在前端
	List<Tag> listTagTop(Integer size);
}
