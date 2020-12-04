package com.example.iBook.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="comments")
public class Comment {
	@Id
	@Column(name="comment_id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="comment_user_id", nullable  = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="comment_post_id", nullable  = false)
	private Post post;
	
	@Column(name="comment_text")
	private String text;
	
	@Column(name="comment_date")
	private Date date;
	

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

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
}





