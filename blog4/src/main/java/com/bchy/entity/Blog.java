package com.bchy.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "t_blog")
public class Blog {
	@Id
	@GeneratedValue
	private Long id;
	// 标题
	private String title;
	// 内容
	private String content;
	// 首图
	private String firstPicture;
	// 标记 原创、转载
	private String flag;
	// 点赞次数
    private Integer thumbsUpCount=0;
    //博客和点赞关联表
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "t_blog_thumbsup", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "thumbsup_id", referencedColumnName = "id"))
	private List<ThumbsUp> thumbsUps;
    
	// 浏览次数
	private Integer views;
	// 是否开启赞赏
	private boolean appreciation;
	// 是否开启转载声明
	private boolean shareStatement;
	// 是否开启评论
	private boolean commentabled;
	// 文章概述
	private String description;
	// 是否发布或保存草稿
	private boolean published;
	// 是否推荐
	private boolean recommend;
	// 创建时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	// 更新时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	// 多个blog对一个type
	@ManyToOne
	@JsonIgnoreProperties({"blogs"})
	private Type type;

	// 多个blog对多个tag
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"blogs"})
	private List<Tag> tags = new ArrayList<Tag>();

	// 多个blog对一个user
	@ManyToOne
	@JsonIgnoreProperties({"blogs"})
	private User user;

	// 一个blog对多个comment
	@OneToMany(mappedBy = "blog")
	@JsonIgnoreProperties({"blog"})
	private List<Comment> comments = new ArrayList<Comment>();

	@Transient // 不会加入数据库只做参数用
	private String tagIds;

	public Blog() {

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFirstPicture() {
		return firstPicture;
	}

	public void setFirstPicture(String firstPicture) {
		this.firstPicture = firstPicture;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public boolean isAppreciation() {
		return appreciation;
	}

	public void setAppreciation(boolean appreciation) {
		this.appreciation = appreciation;
	}

	public boolean isShareStatement() {
		return shareStatement;
	}

	public void setShareStatement(boolean shareStatement) {
		this.shareStatement = shareStatement;
	}

	public boolean isCommentabled() {
		return commentabled;
	}

	public void setCommentabled(boolean commentabled) {
		this.commentabled = commentabled;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getThumbsUpCount() {
		return thumbsUpCount;
	}

	public void setThumbsUpCount(Integer thumbsUpCount) {
		this.thumbsUpCount = thumbsUpCount;
	}

	public List<ThumbsUp> getThumbsUps() {
		return thumbsUps;
	}

	public void setThumbsUps(List<ThumbsUp> thumbsUps) {
		this.thumbsUps = thumbsUps;
	}
	
	/**
	 * 点赞
	 * @param vote
	 * @return
	 */
	public boolean addThumbsUp(ThumbsUp thumbsUp) {
		boolean isExist = false;
		// 判断重复
		for (int index=0; index < this.thumbsUps.size(); index ++ ) {
			if (this.thumbsUps.get(index).getUser().getId() == thumbsUp.getUser().getId()) {
				isExist = true;
				break;
			}
		}
		
		if (!isExist) {
			this.thumbsUps.add(thumbsUp);
			this.thumbsUpCount = this.thumbsUps.size();
		}

		return isExist;
	}

	/**
	 * 取消点赞
	 * @param voteId
	 */
	public void removeThumbsUp(Long thumbsUpId) {
		for (int index=0; index < this.thumbsUps.size(); index ++ ) {
			if (this.thumbsUps.get(index).getId() == thumbsUpId) {
				this.thumbsUps.remove(index);
				break;
			}
		}
		
		this.thumbsUpCount = this.thumbsUps.size();
	}
	
	@Override
	public String toString() {
		return "Blog [id=" + id + ", title=" + title + ", content=" + content + ", firstPicture=" + firstPicture
				+ ", flag=" + flag + ", views=" + views + ", appreciation=" + appreciation + ", shareStatement="
				+ shareStatement + ", commentabled=" + commentabled + ", published=" + published + ", recommend="
				+ recommend + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
}
