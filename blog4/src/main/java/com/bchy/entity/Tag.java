package com.bchy.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author liusong
 * @date 2019年11月25日
 */
@Entity
@Table(name="t_tag")
public class Tag {
	@Id
	@GeneratedValue
	private Long id;

	private String name;
	
	@ManyToMany(mappedBy="tags")
	@JsonIgnoreProperties({"tags"})
	private List<Blog> blogs=new ArrayList<Blog>();

	public Tag(){

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + "]";
	}


}
