package com.bchy.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bchy.entity.Blog;
import com.bchy.service.BlogService;

@Controller
@RequestMapping("/archive")
public class ArchiveShowController {
	
	@Autowired
	private BlogService blogService;

	/**
	 * 游客访问通过归档获取博客列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/getArchive")
	public String getArchive(Model model){
		//查询按照时间进行博客归档信息
		Map<String, List<Blog>> map = blogService.archiveBlog();
		model.addAttribute("archiveMap", map);
		//查询博客总条数
		long count=blogService.getBlogCount();
		model.addAttribute("blogCount",count);
		return "tourist/archive";
	}
	
	/**
	 * 会员访问通过归档获取博客列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/getArchive")
	public String getUserArchive(Model model){
		//查询按照时间进行博客归档信息
		Map<String, List<Blog>> map = blogService.archiveBlog();
		model.addAttribute("archiveMap", map);
		//查询博客总条数
		long count=blogService.getBlogCount();
		model.addAttribute("blogCount",count);
		return "archive";
	}
}
