package com.bchy.web.admin;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bchy.entity.Blog;
import com.bchy.entity.Result;
import com.bchy.entity.User;
import com.bchy.service.BlogService;
import com.bchy.service.TagService;
import com.bchy.service.TypeService;

import scala.annotation.meta.setter;

@Controller
@RequestMapping("/admin")
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired 
	private TypeService typeService;
	@Autowired 
	private TagService tagService;


	/* ***********************************************APP端***************************************** */
    //手机端下拉刷新
	@RequestMapping(value="/app/appGetAll",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Result> appGetAll(@PageableDefault(value=5,page=0,sort={"updateTime"},direction =Sort.Direction.DESC) Pageable pageable){
		Page<Blog> page=blogService.listBlog(pageable);
		return ResponseEntity.ok().body(new Result(100, "获取成功！", page));
	}
	//手机端博客点赞
	@RequestMapping(value="/app/thumbsUp",method=RequestMethod.GET)
	@ResponseBody
	public Result thumbsUp(Long id){
		Result result=new Result();
		Blog blog=blogService.getBlog(id);
		//int thumbsUp= blog.getThumbsUp()+1;
		//boolean b=blogService.updateById(id,thumbsUp);
		/*if(b){
			result.setCode(100);
			result.setDesc("点赞成功！");
		}else{
			result.setCode(200);
			result.setDesc("点赞失败！");
		}*/
		return result;
	}
	//手机端查看选中博客信息
	@RequestMapping(value="/app/oneBlog",method=RequestMethod.GET)
	@ResponseBody
	public Result oneBlog(Long id){
		Result result=new Result();
		Blog blog=blogService.getBlog(id);
		if(blog!=null){
			result.setCode(100);
			result.setDesc("查询成功！");
			result.setData(blog);
		}else{
			result.setCode(200);
			result.setDesc("查询失败！");
			result.setData(blog);
		}
		return result;
	}
	
	
	/* *********************************************WEB端****************************************** */
	/**
	 * web查询所有博客列表
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/blogList",method=RequestMethod.GET)
	public String blogList(Model model,Integer pageNum){
		if(pageNum==null){
			pageNum=1;
		}
		Sort sort=new Sort(Sort.Direction.DESC,"id");
		Pageable pageable=new PageRequest(pageNum-1,10,sort);
		model.addAttribute("page", blogService.listBlog(pageable));
		model.addAttribute("typeList", typeService.allType());
		return "admin/blog";
	}
	/**
	 * 条件搜索博客列表
	 * @param pageable
	 * @param blog
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public String search(@PageableDefault(value=10,sort={"id"},direction =Sort.Direction.DESC) Pageable pageable,Blog blog,Model model){
		model.addAttribute("page", blogService.searchBlog(pageable,blog));
		return "admin/blog";
	}

	/**
	 * 跳转到添加博客页面
	 * @return
	 */
	@RequestMapping(value="/toAddBlogPage",method=RequestMethod.GET)
	public String toAddBlogPage(Model model){
		model.addAttribute("types", typeService.allType());
		model.addAttribute("tags", tagService.allTag());
		model.addAttribute("blog", new Blog());
		return "admin/blog_input";
	}

	@Transactional
	@RequestMapping(value="/addBlog",method=RequestMethod.POST)
	public String addBlog(Blog blog,long typeId,String tagIds,Model moel,HttpSession session){
		blog.setUser((User)session.getAttribute("user"));
		blog.setType(typeService.getType(typeId));
		blog.setTags(tagService.listTag(blog.getTagIds()));
		blogService.addBlog(blog);
		return "redirect:/admin/blog";
	}

}
