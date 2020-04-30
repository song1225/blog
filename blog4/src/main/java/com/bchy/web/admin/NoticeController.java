package com.bchy.web.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bchy.entity.Notice;
import com.bchy.entity.ReadNotice;
import com.bchy.entity.Result;
import com.bchy.entity.User;
import com.bchy.service.NoticeService;
import com.bchy.service.ReadNoticeService;

@Controller
@RequestMapping("/admin")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private ReadNoticeService readNoticeService;

	@RequestMapping("/noticeList")
	public String noticeList(Model model,Integer pageNum) {
		if(pageNum==null){
			pageNum=1;
		}
		 Sort sort = new Sort(Sort.Direction.DESC,"id");	 
		 Pageable pageable = new PageRequest(pageNum-1,10,sort);
		model.addAttribute("page",noticeService.list(pageable));
		return "admin/notice";
	}
	
	@RequestMapping(value="/addNotice",method=RequestMethod.POST)
	@ResponseBody
	public Result addNotice(Notice notice,HttpSession session){
		Result result=new Result();
		User user=(User) session.getAttribute("user");
		if(user!=null){
			notice.setUser(user);
			notice.setCreateTime(new Date());
			notice.setUpdateTime(new Date());
		}
		Notice n=noticeService.addNotice(notice);
		if(n!=null){
			result.setCode(100);
			result.setDesc("公告发布成功！");
			result.setData(n);
		}else{
			result.setCode(200);
			result.setDesc("公告发布失败！");
		}
		return result;
	}
	
	@RequestMapping("/notice/{id}")
	public String getNotice(@PathVariable Long id,Model model,HttpSession session){
		Notice notice=noticeService.findById(id);
		User user=(User) session.getAttribute("user");
		ReadNotice r=new ReadNotice(notice, user, 1);
		readNoticeService.addReadNotice(r);
		model.addAttribute("title",notice.getTitle());
		model.addAttribute("text",notice.getText());
		return "index::noticeShow";
	}
	@RequestMapping("/deleteNotice")
	@ResponseBody
	public Result deleteNotice(@RequestParam("noticeId") Long noticeId){
		Result result=new Result();
		//删除已经查看消息表数据
		readNoticeService.deleteByNoticeId(noticeId);
		int i=noticeService.deleteNoticeById(noticeId);
		if(i>0){
			result.setCode(100);
		}
		return result;
	}
}
