package com.bchy.web.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bchy.entity.EchartsData;
import com.bchy.entity.Result;
import com.bchy.entity.User;
import com.bchy.service.BlogService;
import com.bchy.service.CollectionService;
import com.bchy.service.CommentService;
import com.bchy.service.ThumbsUpService;
import com.bchy.service.UserService;

@Controller
@RequestMapping("/admin")
public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private BlogService blogService;
	@Autowired
	private ThumbsUpService thumbsUpService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private CollectionService collectionService;

	/*
	 * *******************************APP访问地址***********************************
	 * **********
	 */
	@RequestMapping(value = "/app/userList", method = RequestMethod.GET)
	@ResponseBody
	public Result userList(String username) {
		Result result = new Result();
		List<User> userList = userService.findUserExcludeMe(username);
		if (userList.size() > 0) {
			result.setCode(100);
			result.setDesc("查询用户成功！");
			result.setData(userList);
		} else {
			result.setCode(200);
			result.setDesc("查询用户失败！");
		}
		return result;
	}

	/*
	 * *******************************WEB访问地址***********************************
	 * **********
	 */
	@RequestMapping(value = "/toAdminPage", method = RequestMethod.GET)
	public String toAdminPage(Model model) {
		int userCount = userService.getAllCount();
		int blogCount=blogService.getAllCount();
		int sumViews=blogService.sumViews();
		model.addAttribute("userCount", userCount);
		model.addAttribute("blogCount", blogCount);
		model.addAttribute("sumViews", sumViews);
		return "admin/main";
	}
	
	
	@RequestMapping(value="/getCharts",method=RequestMethod.POST)
	@ResponseBody
	public List<EchartsData> getCharts(){
		List<EchartsData> list=new ArrayList<EchartsData>();
		//发布量
		int blogCount=blogService.getAllCount();
		//阅读量
		int sumViews=blogService.sumViews();
		//点赞量
		int thumbsupCount=thumbsUpService.getAllCount();
		//评论量
		int commentCount=commentService.getAllCount();
		//收藏量
		int collectionCount=collectionService.getAllCount();
		list.add(new EchartsData("发布量",blogCount));
		list.add(new EchartsData("阅读量",sumViews));
		list.add(new EchartsData("点赞量",thumbsupCount));
		list.add(new EchartsData("评论量",commentCount));
		list.add(new EchartsData("收藏量",collectionCount));
		return list;
	}

	/**
	 * 
	 * @return 跳转到登陆界面
	 */
	@RequestMapping(value = "/toLoginPage", method = RequestMethod.GET)
	public String loginPage() {
		return "admin/login";
	}

	/**
	 * 用户登陆方法
	 * 
	 * @param username
	 * @param password
	 * @param session
	 *            保存用户信息
	 * @param attributes
	 *            重定向之后保证可以获取错误信息
	 * @return
	 */
	@RequestMapping(value = "web/login", method = RequestMethod.POST)
	public String webLogin(String username, String password, HttpSession session, RedirectAttributes attributes) {
		User user = userService.checkUser(username, password);
		if (user != null) {
			// 密码设置为空，前台获取不到密码，保证账户安全
			user.setPassword(null);
			session.setAttribute("user", user);
			return "admin/index";
		} else {
			attributes.addFlashAttribute("message", "用户名或密码错误");
			return "redirect:toLoginPage";
		}

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Result login(String username, String password, HttpSession session, RedirectAttributes attributes) {
		Result res = new Result();
		User user = userService.checkUser(username, password);
		if (user != null) {
			// 密码设置为空，前台获取不到密码，保证账户安全
			user.setPassword(null);
			session.setAttribute("user", user);
			res.setCode(100);
			res.setDesc("用户名密码正确");
			res.setData(user);
		} else {
			res.setCode(200);
			res.setDesc("用户名或密码错误");
		}
		return res;
	}

	@RequestMapping(value = "/toWebIndex", method = RequestMethod.GET)
	public String toWebIndex() {
		return "admin/index";
	}

	/**
	 * 
	 * @param session
	 *            清空session中保存信息，跳转到登陆界面
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:toLoginPage";
	}
}
