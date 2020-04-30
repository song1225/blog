package com.bchy.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bchy.entity.Comment;
import com.bchy.entity.Result;
import com.bchy.entity.User;
import com.bchy.service.BlogService;
import com.bchy.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private BlogService blogService;
	
	@Value("${common.avatar}")
	private String avatar;
	
	@RequestMapping("/getComments/{blogId}")
	public String comments(@PathVariable Long blogId,Model model){
		model.addAttribute("comments",commentService.listCommentsByBlogId(blogId));
		return "blog :: commentList";
	}
    
	@RequestMapping(value="/addComment",method=RequestMethod.POST)
	public String addComment(Comment comment,HttpSession session){
	    Long blogId=comment.getBlog().getId();
	    comment.setBlog(blogService.getBlog(blogId));
	    User user=(User) session.getAttribute("user");
	    if(user!=null){
	    	comment.setAvatar(user.getAvatar());
	    	comment.setAdminComment(true);
	    }else{
	    	comment.setAvatar(avatar);
	    } 
	    commentService.addComment(comment);
		return "redirect:/comment/getComments/"+blogId;
	}
}
