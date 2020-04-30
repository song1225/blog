package com.bchy.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_log")
public class Log {
	@Id
	@GeneratedValue
	private Long id;
	@OneToOne
	private User user;

	private Integer state;// 日志类型 0：登录 1：发布博客

	private boolean successOrFalse; // 是否操作成功

	// 创建日志时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	public Log() {

	}

	public Log(User user, Integer state, boolean successOrFalse, Date createTime) {
		super();
		this.user = user;
		this.state = state;
		this.successOrFalse = successOrFalse;
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public boolean isSuccessOrFalse() {
		return successOrFalse;
	}

	public void setSuccessOrFalse(boolean successOrFalse) {
		this.successOrFalse = successOrFalse;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
