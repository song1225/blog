package com.bchy.web.admin;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bchy.entity.User;
import com.bchy.service.UserService;
import com.bchy.utils.FileUtil;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	private UserService userService;

	@RequestMapping("/toRegisterPage")
	public String toRegisterPage() {
		return "/admin/register";
	}

	@RequestMapping(value = "/userRegister", method = RequestMethod.POST)
	public String userRegister(User user, Model model, @RequestParam("file") MultipartFile file,
			HttpServletRequest request) throws IllegalStateException, IOException {
		String avatar="";
		if(!file.isEmpty()){
			String filename = file.getOriginalFilename();
			//检查是否为IE浏览器，修改filename路径为文件名
			int unixSep = filename.lastIndexOf('/');	
			// Check for Windows-style path		
			int winSep = filename.lastIndexOf('\\');
			// Cut off at latest possible point		
			int pos = (winSep > unixSep ? winSep : unixSep);
			if (pos != -1)  {			
					
				filename = filename.substring(pos + 1);	
				}
			 // 存放上传图片的文件夹
	        File fileDir = FileUtil.getImgDirFile();
	        File newFile = new File(fileDir.getAbsolutePath() + File.separator + filename);       
	        file.transferTo(newFile);	       
	        //截取地址保存至数据库
	        String realFile=fileDir.getAbsolutePath() + File.separator + filename;
	        int begin=realFile.indexOf("\\upload");
	        int end=realFile.length();
	        avatar=realFile.substring(begin, end);
		}
		user.setAvatar(avatar);
		userService.addUser(user);
		return "redirect:/user/toLoginPage";
	}

}
