package com.bchy.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="t_comment")
public class Comment {
	@Id
	@GeneratedValue
	private Long id;
	//昵称
	private String nickname;
	//邮箱
	private String email;
	//内容
	private String content;
	//头像
	private String avatar;
	//创建时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@ManyToOne
	@JsonIgnoreProperties({"comments"})
	private Blog blog;

	//评论与回复信息关联关系
	@OneToMany(mappedBy="parentComment")
	private List<Comment> replyComments=new ArrayList<Comment>();
	@ManyToOne
	private Comment parentComment;
	
	//是否为管理员回复消息
	private boolean adminComment;

	public Comment(){

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}


	public List<Comment> getReplyComments() {
		return replyComments;
	}

	public void setReplyComments(List<Comment> replyComments) {
		this.replyComments = replyComments;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}
	

	public boolean isAdminComment() {
		return adminComment;
	}

	public void setAdminComment(boolean adminComment) {
		this.adminComment = adminComment;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", nickname=" + nickname + ", email=" + email + ", content=" + content
				+ ", avatar=" + avatar + ", createTime=" + createTime + "]";
	}
}
