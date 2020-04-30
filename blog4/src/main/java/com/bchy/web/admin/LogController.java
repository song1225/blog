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
import com.bchy.service.LogService;

import sun.applet.resources.MsgAppletViewer;

@Controller
@RequestMapping("/admin")
public class LogController {

	@Autowired
	private LogService logService;

	@RequestMapping("/logList")
	public String LogList(Model model,Integer pageNum) {
		if(pageNum==null){
			pageNum=1;
		}
		 Sort sort = new Sort(Sort.Direction.DESC,"id");	 
		 Pageable pageable = new PageRequest(pageNum-1,10,sort);
		model.addAttribute("page", logService.list(pageable));
		return "admin/log";
	}
	
	@RequestMapping("/deleteLog")
	@ResponseBody
	public Result deleteLog(@RequestParam("logId") Long logId){
		Result result=new Result();
		int i=logService.deleteLogById(logId);
		if(i>0){
			result.setCode(100);
		}
		return result;
	}
}
