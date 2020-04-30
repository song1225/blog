package com.bchy.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class AboutShowController {
    @RequestMapping("/aboutMe")
	public String aboutMe(){
		return "tourist/about";
	}
    
    @RequestMapping("/user/aboutMe")
	public String aboutUserMe(){
		return "about";
	}
}
