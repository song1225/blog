package com.bchy.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bchy.entity.Blog;
import com.bchy.entity.Tag;
import com.bchy.service.BlogService;
import com.bchy.service.TagService;

@Controller
@RequestMapping("/tag")
public class TagShowController {
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private BlogService blogService;

	/**
	 * 游客访问通过Tag标签分类的博客列表
	 * @param pageable
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/getTags/{id}")
	public String getTags(
			@PageableDefault(size = 5, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
			@PathVariable Long id,Model model) {
		List<Tag> tags=tagService.listTagTop(10000);
		if(id==-1){
			id=tags.get(0).getId();
		}
		Page<Blog> page=blogService.listBlogByTagId(pageable,id);
		model.addAttribute("tags", tags);
		model.addAttribute("page", page);
		model.addAttribute("activeTagId",id);
		return "tourist/tag";
	}
	
	/**
	 * 会员访问通过Tag标签分类的博客列表
	 * @param pageable
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/getTags/{id}")
	public String getUserTags(
			@PageableDefault(size = 5, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
			@PathVariable Long id,Model model) {
		List<Tag> tags=tagService.listTagTop(10000);
		if(id==-1){
			id=tags.get(0).getId();
		}
		Page<Blog> page=blogService.listBlogByTagId(pageable,id);
		model.addAttribute("tags", tags);
		model.addAttribute("page", page);
		model.addAttribute("activeTagId",id);
		return "tag";
	}
}
