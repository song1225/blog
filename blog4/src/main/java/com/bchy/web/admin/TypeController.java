package com.bchy.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bchy.entity.Result;
import com.bchy.entity.Type;
import com.bchy.service.TypeService;

@Controller
@RequestMapping("/admin")
public class TypeController {

	@Autowired
	private TypeService typeService;

	/**
	 * 分页查询数据保存为page
	 * @param pageable 分页对象
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/typeList",method=RequestMethod.GET)
	public String typeList(Model model,Integer pageNum){
		if(pageNum==null){
			pageNum=1;
		}
		Sort sort=new Sort(Sort.Direction.DESC,"id");
		Pageable pageable=new PageRequest(pageNum-1, 10,sort);
		model.addAttribute("page",typeService.listType(pageable));
		return "admin/type";
	}

	/**
	 * 跳转到新增分类界面
	 * @return
	 */
	@RequestMapping(value="/toAddTypePage",method=RequestMethod.GET)
	public String toAddTypePage(){
		return "admin/type_input";
	}

	/**
	 * 新增分类
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/addType",method=RequestMethod.POST)
	@ResponseBody
	public Result addType(String typeName){
		Result result=new Result();
		Type type=new Type();
		type.setName(typeName);
		Type t=typeService.saveType(type);
		if(t!=null){
			result.setCode(100);
			result.setDesc("分类新增成功！");
			result.setData(t);
		}else{
			result.setCode(200);
			result.setDesc("分类新增失败！");
		}
		return result;
	}

	/**
	 * 通过ID删除分类
	 * @param id
	 */
	@RequestMapping(value="/deleteType/{id}",method=RequestMethod.GET)
	public String deleteType(@PathVariable Long id,RedirectAttributes attributes){
		typeService.deleteType(id);
		attributes.addFlashAttribute("message", "删除成功！");
		return "redirect:/admin/type";
	}
}
