package com.bchy.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bchy.entity.Collection;
import com.bchy.entity.Result;
import com.bchy.entity.User;
import com.bchy.service.CollectionService;

@Controller
@RequestMapping("/collection")
public class CollectionController {
	@Autowired
	private CollectionService collectionService;

	/**
	 * 会员添加收藏
	 * 
	 * @param blogId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/addCollection")
	@ResponseBody
	public Result addCollection(Long blogId, HttpSession session) {
		Result result = new Result();
		User user = (User) session.getAttribute("user");
		Collection collection = collectionService.addCollection(blogId, user.getId());
		if (collection != null) {
			result.setCode(100);
			result.setDesc("收藏成功");
			result.setData(collection);
		} else {
			result.setCode(200);
			result.setDesc("收藏失败");
		}
		return result;
	}

	/**
	 * 会员取消收藏
	 * 
	 * @param blogId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/deleteCollection")
	@ResponseBody
	public Result deleteCollection(Long blogId, HttpSession session) {
		Result result = new Result();
		User user = (User) session.getAttribute("user");
		collectionService.deleteCollection(blogId, user.getId());
		result.setCode(100);
		result.setDesc("取消收藏成功");
		return result;
	}
}
