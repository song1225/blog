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
import com.bchy.entity.Tag;
import com.bchy.service.TagService;

@Controller
@RequestMapping("/admin")
public class TagController {

	@Autowired
	private TagService tagService;

	/**
	 * 分页查询
	 * 
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/tagList", method = RequestMethod.GET)
	public String tagList(Model model,Integer pageNum) {
		if(pageNum==null){
			pageNum=1;
		}
		Sort sort=new Sort(Sort.Direction.DESC,"id");
		Pageable pageable=new PageRequest(pageNum-1, 10,sort);
		model.addAttribute("page", tagService.listTag(pageable));
		return "admin/tag";
	}

	/**
	 * 跳转到标签添加页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toAddTagPage", method = RequestMethod.GET)
	public String toAddTagPage() {
		return "admin/tag_input";
	}

	/**
	 * 新增标签，并将mesage返回页面
	 * 
	 * @param tag
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/addTag", method = RequestMethod.POST)
	@ResponseBody
	public Result addTag(String tagName) {
		Result result = new Result();
		Tag tag = new Tag();
		tag.setName(tagName);
		Tag t = tagService.addTag(tag);
		if (t != null) {
			result.setCode(100);
			result.setDesc("标签添加成功！");
			result.setData(t);
		} else {
			result.setCode(200);
			result.setDesc("标签添加失败！");
		}
		return result;
	}

	/**
	 * 删除标签
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteTag/{id}", method = RequestMethod.GET)
	public String deleteTag(@PathVariable Long id, RedirectAttributes attributes) {
		tagService.deleteTag(id);
		attributes.addFlashAttribute("message", "标签删除成功!");
		return "redirect:/admin/tag";
	}
}
