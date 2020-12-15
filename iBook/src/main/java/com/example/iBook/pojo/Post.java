package com.example.iBook.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="posts")
@SecondaryTable(name="posts_comments")
public class Post {
	@Id
	@Column(name="posts_id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="posts_user_id", nullable=false)
	private User user;
	
	@Column(name="posts_content")
	private String content;
	
	@Column(name="posts_date")
	private Date date;
	
	
	@Column(name="comment_number", table="posts_comments", insertable=false, updatable=false)
	private Integer commentCount;
	
	@Transient
	private boolean editable;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}




	
	

}