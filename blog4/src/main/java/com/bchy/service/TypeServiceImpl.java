package com.bchy.service;



import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bchy.dao.TypeRepository;
import com.bchy.entity.Type;

import javassist.NotFoundException;
@Service
public class TypeServiceImpl implements TypeService {

	@Autowired
	private TypeRepository typeRepository;

	@Transactional
	@Override
	public Type saveType(Type type) {
		return typeRepository.save(type);
	}

	@Transactional
	@Override
	public Type getType(Long id) {
		return typeRepository.findOne(id);
	}

	@Transactional
	@Override
	public Page<Type> listType(Pageable pageable) {
		return typeRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public Type updateType(Long id, Type type) {
		Type t=typeRepository.findOne(id);
		if(t==null){
			try {
				throw new NotFoundException("不存在该类型");
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//此方法把修改后数据赋值给数据库查到值，实现更新
		BeanUtils.copyProperties(type, t);
		return typeRepository.save(t);
	}

	@Transactional
	@Override
	public void deleteType(Long id) {
		typeRepository.delete(id);
	}

	@Override
	public List<Type> allType() {
		return typeRepository.findAll();
	}

	
	//查询使用做多的类型集合，显示为top
	@Override
	public List<Type> listTypeTop(Integer size) {
		Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
		Pageable pageable=new PageRequest(0,size,sort);
		return typeRepository.findTop(pageable);
	}

}
