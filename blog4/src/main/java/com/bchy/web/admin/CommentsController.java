package com.bchy.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bchy.entity.Result;
import com.bchy.service.CommentService;

@Controller
@RequestMapping("/admin")
public class CommentsController {
	@Autowired
	private CommentService commentService;

	@RequestMapping("/commentList")
	public String LogList(Model model,Integer pageNum) {
		if(pageNum==null){
			pageNum=1;
		}
		 Sort sort = new Sort(Sort.Direction.DESC,"id");	 
		 Pageable pageable = new PageRequest(pageNum-1,10,sort);
		model.addAttribute("page", commentService.listComment(pageable));
		return "admin/comment";
	}
	
	@RequestMapping("/deleteComment")
	@ResponseBody
	public Result deleteComment(@RequestParam("commentId") Long commentId){
		Result result=new Result();
		int i=commentService.deleteCommentById(commentId);
		if(i>0){
			result.setCode(100);
		}
		return result;
	}
}
