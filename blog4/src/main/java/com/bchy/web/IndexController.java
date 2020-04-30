package com.bchy.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bchy.entity.Blog;
import com.bchy.entity.ThumbsUp;
import com.bchy.entity.User;
import com.bchy.service.BlogService;
import com.bchy.service.CommentService;
import com.bchy.service.TagService;
import com.bchy.service.TypeService;

@Controller
public class IndexController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private TypeService typeService;

	@Autowired
	private TagService tagService;

	/**
	 * 进入博客信息列表界面
	 * 
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
	public String index(@PageableDefault(value = 5, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
			Model model) {
		model.addAttribute("page", blogService.listBlog(pageable));
		model.addAttribute("types", typeService.listTypeTop(6));
		model.addAttribute("tags", tagService.listTagTop(10));
		model.addAttribute("recommendBlogs", blogService.listBlogTop(6));
		return "tourist/index";
	}
	
	/**
	 * 会员访问首页
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping("/user")
	public String userIndex(@PageableDefault(value = 5, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
			Model model) {
		model.addAttribute("page", blogService.listBlog(pageable));
		model.addAttribute("types", typeService.listTypeTop(6));
		model.addAttribute("tags", tagService.listTagTop(10));
		model.addAttribute("recommendBlogs", blogService.listBlogTop(6));
		return "index";
	}

	/**
	 * 游客通过输入框查询到的搜索结果界面
	 * 
	 * @param pageable
	 * @param model
	 * @param search
	 * @return
	 */
	@RequestMapping("/search")
	public String search(
			@PageableDefault(value = 5, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
			Model model, String search) {
		model.addAttribute("page", blogService.searchList(pageable, "%" + search + "%"));
		model.addAttribute("search", search);
		return "tourist/search";
	}
	
	/**
	 * 会员通过输入框查询到的搜索结果界面
	 * 
	 * @param pageable
	 * @param model
	 * @param search
	 * @return
	 */
	@RequestMapping("/user/search")
	public String userSearch(
			@PageableDefault(value = 5, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
			Model model, String search) {
		model.addAttribute("page", blogService.searchList(pageable, "%" + search + "%"));
		model.addAttribute("search", search);
		return "search";
	}

	/**
	 * 进入博客详情页
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/blog/{id}")
	public String blogInfo(@PathVariable Long id, Model model) {
		// 获取上下文中session中用户信息
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		User user = (User) request.getSession().getAttribute("user");
		// 获取博客点赞情况，并判断用户是否已经点赞
		ThumbsUp currentThumbsUp = null;
		Blog blog = blogService.getBlog(id);
		List<ThumbsUp> thumbsUps = blog.getThumbsUps();
		if (user != null) {
			for (ThumbsUp thumbsUp : thumbsUps) {
				System.out.println(thumbsUp.toString());
				if (thumbsUp.getUser().getUsername() != "" && thumbsUp.getUser().getUsername() != null) {

					if (thumbsUp.getUser().getUsername().equals(user.getUsername())) {
						currentThumbsUp = thumbsUp;
					} else {
						currentThumbsUp = null;
					}
				}

			}
		}
		model.addAttribute("blog", blogService.getBlogConvertHtml(id));
		model.addAttribute("currentThumbsUp", currentThumbsUp);
		return "tourist/blog";
	}
}
