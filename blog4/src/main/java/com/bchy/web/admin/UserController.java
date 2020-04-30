package com.bchy.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bchy.entity.User;
import com.bchy.service.UserService;

@Controller
@RequestMapping("/admin")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/getUserList")
	public String getUserList(Model model,Integer pageNum) {
		if(pageNum==null){
			pageNum=1;
		}
		Sort sort=new Sort(Sort.Direction.DESC,"id");
		Pageable pageable=new PageRequest(pageNum-1, 10,sort);
		model.addAttribute("page", userService.listUser(pageable));
		return "admin/user";
	}
	
	@RequestMapping("/user/{id}")
	public String getUser(@PathVariable Long id,Model model){
		model.addAttribute("user", userService.getUserById(id));
		return "admin/user::userForm";
	}
}
