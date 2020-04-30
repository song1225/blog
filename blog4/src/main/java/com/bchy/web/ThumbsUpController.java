package com.bchy.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bchy.entity.Blog;
import com.bchy.entity.Result;
import com.bchy.entity.User;
import com.bchy.service.BlogService;
import com.bchy.service.ThumbsUpService;

@Controller
@RequestMapping("/thumbsUp")
public class ThumbsUpController {

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private ThumbsUpService thumbsUpService;
	
	
	/**
	 * 实现点赞功能
	 * @param blogId
	 * @return
	 */
	@RequestMapping(value="/addThumbsUp/{blogId}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Result> addThumbsUp(@PathVariable Long blogId){
		Result result=new Result();
		Blog blog=blogService.addThumbsUp(blogId);
		if(blog!=null){
			result.setCode(200);
			result.setDesc("点赞失败！");
			result.setData(blog);
		}else{
			result.setCode(100);
			result.setDesc("点赞成功！");
			result.setData(blog);
		}
		return ResponseEntity.ok().body(new Result(100,"点赞成功",null));
	}
	
	@RequestMapping(value="/deleteThumbsUp/{thumbsUpId}/{blogId}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Result> deleteThumbsUp(@PathVariable Long thumbsUpId,@PathVariable Long blogId){
		//获取点赞的用户信息
		User user=thumbsUpService.getThumbsUpById(thumbsUpId).getUser();
		//判断登录用户与点赞用户是否一致
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		User user1=(User) request.getSession().getAttribute("user");
		//是否为点赞者表示
		boolean isOwner = false;
		if(user1!=null&&user.getUsername().equals(user1.getUsername())){
			isOwner=true;
			blogService.removeThumbsUp(blogId, thumbsUpId);
			thumbsUpService.removeThumbsUp(thumbsUpId);
		}else{
			isOwner = false;
		}
		return ResponseEntity.ok().body(new Result(100,"取消点赞成功",null));	
	}
}
