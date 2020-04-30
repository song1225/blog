package com.bchy.entity;

import java.io.Serializable;
import java.sql.Timestamp;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 点赞实体
 * 
 * @author liusong
 *
 * @date 2019年12月25日
 */
@SuppressWarnings("all")
@Entity
@Table(name = "t_thumbsup")
public class ThumbsUp{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增
	private long id;
	
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(nullable = false) // 映射为字段，值不能为空
	@org.hibernate.annotations.CreationTimestamp  // 由数据库自动创建时间
	private Timestamp createTime;

	public ThumbsUp() {

	}

	public ThumbsUp(User user) {
		this.user = user;
	}

	public ThumbsUp(long id, User user, Timestamp createTime) {
		super();
		this.id = id;
		this.user = user;
		this.createTime = createTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ThumbsUp [id=" + id + ", user=" + user + ", createTime=" + createTime + "]";
	}
	
}
