package com.bchy.web;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bchy.entity.Gallery;
import com.bchy.entity.User;
import com.bchy.service.GalleryService;
import com.bchy.utils.FileUtil;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	
	@Autowired
	private GalleryService galleryService;
	
	@RequestMapping(value="/updateGallery",method=RequestMethod.POST)
	public String updateGallery(@RequestParam("picture") MultipartFile picture,
	     HttpSession session) throws IllegalStateException, IOException{
		User user=(User)session.getAttribute("user");
		String pic="";
		if(!picture.isEmpty()){
			String filename = picture.getOriginalFilename();
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
	        File fileDir = FileUtil.getPictureDirFile(user.getId().toString());
	        File newFile = new File(fileDir.getAbsolutePath() + File.separator + filename);       
	        picture.transferTo(newFile);	       
	        //截取地址保存至数据库
	        String realFile=fileDir.getAbsolutePath() + File.separator + filename;
	        int begin=realFile.indexOf("\\upload");
	        int end=realFile.length();
	        pic=realFile.substring(begin, end);
	        Gallery gallery=new Gallery();
	        gallery.setPicture(pic);
	        gallery.setUser(user);
	        gallery.setCreateTime(new Date());
	        Gallery g=galleryService.save(gallery);
		}
		return "redirect:/user/galleryList";
	}
	
	@RequestMapping(value="/deletePicture")
	public String deletePicture(@RequestParam(value="id") long id,RedirectAttributes attribute){
		galleryService.deleteById(id);
		attribute.addFlashAttribute("success", "恭喜您，删除成功！");
		return "redirect:/user/galleryList";
	}

}
