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
@Table(name = "t_notice")
public class Notice {
	@Id
	@GeneratedValue
	private Long id;
	
	private String title;// 标题
	
	private String text;// 内容
	
	private boolean showNotice;// 前端显示
	
	@OneToOne
	private User user; //发布用户信息
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;// 创建日志时间
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;// 更新日志时间
	
	public Notice() {

	}

	public Notice(Long id, String title, String text, boolean showNotice, User user, Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.showNotice = showNotice;
		this.user = user;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isShowNotice() {
		return showNotice;
	}

	public void setShowNotice(boolean showNotice) {
		this.showNotice = showNotice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
