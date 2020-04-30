package com.bchy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_read")
public class ReadNotice {
	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private Notice notice;

	@OneToOne
	private User user;

	@Column(name="readState")
	private Integer readState;

	public ReadNotice() {
	}

	public ReadNotice(Notice notice, User user, Integer readState) {
		super();
		this.notice = notice;
		this.user = user;
		this.readState = readState;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getReadState() {
		return readState;
	}

	public void setReadState(Integer readState) {
		this.readState = readState;
	}

}
