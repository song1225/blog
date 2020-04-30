package com.bchy.service;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bchy.entity.Type;

public interface TypeService {
	//新增
	Type saveType(Type type);
	
	//根据id查询
	Type getType(Long id);
	
	//分页查询
	Page<Type> listType(Pageable pageable);
	
	//修改
	Type updateType(Long id,Type type);
	
	//删除
	void deleteType(Long id);
	
	//查询所有
	List<Type> allType();
	
	//查询TOP6最多使用的类型
	List<Type> listTypeTop(Integer size);

}
