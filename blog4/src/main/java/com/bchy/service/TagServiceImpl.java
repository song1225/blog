package com.bchy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bchy.dao.TagRepository;
import com.bchy.entity.Tag;
@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagRepository tagRepository;

	@Override
	public Tag addTag(Tag tag) {
		return tagRepository.save(tag);
	}

	@Override
	public Tag getTag(Long id) {
		return tagRepository.findOne(id);
	}

	@Override
	public Page<Tag> listTag(Pageable pageable) {
		return tagRepository.findAll(pageable);
	}

	@Override
	public Tag updateTag(Long id, Tag tag) {
		Tag t=tagRepository.findOne(id);
		BeanUtils.copyProperties(tag, t);
		return tagRepository.save(t);
	}

	@Override
	public void deleteTag(Long id) {
		tagRepository.delete(id);
	}

	@Override
	public List<Tag> allTag() {
		return tagRepository.findAll();
	}

	@Override
	public List<Tag> listTag(String ids) {
		return tagRepository.findAll(convertToList(ids));
	}
	//把博客前端选择的1，2，3转为list
	private List<Long> convertToList(String ids){
		List<Long> list=new ArrayList<>();
		if(!"".equals(ids)&&ids!=null){
			String [] data=ids.split(",");
			for(int i=0;i<data.length;i++){
				list.add(new Long(data[i]));
			}
		}
		return list;
	}

	@Override
	public List<Tag> listTagTop(Integer size) {
		Sort sort =new Sort(Sort.Direction.DESC,"blogs.size");
		Pageable pageable=new PageRequest(0,size,sort);
		return tagRepository.findTop(pageable);
	}

}
