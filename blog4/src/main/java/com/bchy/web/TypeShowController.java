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
import com.bchy.entity.Type;
import com.bchy.service.BlogService;
import com.bchy.service.TypeService;

@Controller
@RequestMapping("/type")
public class TypeShowController {
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private BlogService blogService;

	
	/**
	 * 游客登录获取所有根据Type进行分类博客
	 * @param pageable
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/getTypes/{id}")
	public String getTypes(
			@PageableDefault(size = 5, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
			@PathVariable Long id,Model model) {
		List<Type> listType=typeService.listTypeTop(10000);
		if(id==-1){
			id=listType.get(0).getId();
		}
		Page<Blog> page=blogService.listBlogByTypeId(pageable,id);
		model.addAttribute("types", listType);
		model.addAttribute("page", page);
		model.addAttribute("activeTypeId", id);
		return "tourist/type";
	}
	
	/**
	 * 会员登录获取所有根据Type进行分类博客
	 * @param pageable
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/getTypes/{id}")
	public String getUserTypes(
			@PageableDefault(size = 5, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
			@PathVariable Long id,Model model) {
		List<Type> listType=typeService.listTypeTop(10000);
		if(id==-1){
			id=listType.get(0).getId();
		}
		Page<Blog> page=blogService.listBlogByTypeId(pageable,id);
		model.addAttribute("types", listType);
		model.addAttribute("page", page);
		model.addAttribute("activeTypeId", id);
		return "type";
	}
}
