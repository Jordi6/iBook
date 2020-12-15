package com.example.iBook.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.iBook.pojo.Comment;
import com.example.iBook.pojo.Post;
import com.example.iBook.pojo.User;
import com.example.iBook.repo.CommentRepository;
import com.example.iBook.repo.PostRepository;
import com.example.iBook.repo.UserRepository;

@RestController
public class PostController {
	private UserRepository UserRepo;
	private PostRepository PostRepo;
	private CommentRepository CommentRepo;
	
	@Autowired
	public PostController(UserRepository UserRepo, 
			PostRepository PostRepo,
			CommentRepository CommentRepo) {
		this.UserRepo = UserRepo;
		this.PostRepo = PostRepo;
		this.CommentRepo = CommentRepo;
	}
	
	
	@RequestMapping("/search-posts")
	public List<Post> searchPosts(
			@RequestParam String text,
			@RequestParam int limit,
			@RequestParam int offset) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		Pageable page = PageRequest.of(offset/limit, limit);
		
		List<Post> posts = (List<Post>) PostRepo.findAllByContentContainingOrderByDateDesc(text, page);
		
		for (Post post:posts) {
			if (name.equals(post.getUser().getName())) {
				post.setEditable(true);
			}
		}
		return posts;
	}
	
	
	
	
	
	@RequestMapping("/get-comments")
	public List<Comment> getComments(@RequestParam int postId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		Post post = PostRepo.findById(postId).get();
		List<Comment> comments = (List<Comment>) CommentRepo.findAllByPostOrderByDateAsc(post);
		return comments;
	}
	
	
	@RequestMapping("/get-posts")
	public List<Post> getPosts(@RequestParam int limit, @RequestParam int offset) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		User user = UserRepo.findFirstByName(name);
		Pageable page = PageRequest.of(offset/limit, limit);
		
		List<Post> posts = (List<Post>) PostRepo.findAllByOrderByDateDesc(page);
		
		
		for (Post post :posts) {
			if (name.equals(post.getUser().getName())) {
				post.setEditable(true);
			}
		}
		
		return posts;
	}
	
	
	
	@RequestMapping("/save-post")
	public Post savePost(@RequestParam String content, @RequestParam int id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		User user = UserRepo.findFirstByName(name);
		if (id > 0) {
			Post post = PostRepo.findById(id).get();
			if (user.getName().equals(post.getUser().getName())) {
				post.setContent(content);
				post.setDate(new Date());
				post = PostRepo.save(post);
				return post;
			}
		} else {
			Post post = new Post();
			post.setUser(user);
			post.setContent(content);
			post.setDate(new Date());
			post = PostRepo.save(post);
			return post;			
		}
		return null;
		
	}
	
	@RequestMapping("/save-comment")
	public Comment saveComment(
			@RequestParam String content,
			@RequestParam int id,
			@RequestParam int postId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		User user = UserRepo.findFirstByName(name);
		if (id > 0) {
			Comment comment = CommentRepo.findById(id).get();
			if (user.getName().equals(comment.getUser().getName())) {
				comment.setText(content);
				comment.setDate(new Date());
				comment = CommentRepo.save(comment);
				return comment;
			}
		} else {
			Post post = PostRepo.findById(postId).get();
			Comment comment = new Comment();
			comment.setUser(user);
			comment.setText(content);
			comment.setDate(new Date());
			comment.setPost(post);
			comment = CommentRepo.save(comment);
			return comment;			
		}
		return null;
	}
	
	
	
	
	@RequestMapping("/delete-post")
	public void deletePost(@RequestParam int id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		User user = UserRepo.findFirstByName(name);
		Post post = PostRepo.findById(id).get();
		
		
		if (user.getName().equals(post.getUser().getName())) {
			//PostRepo.delete(post);
			PostRepo.deletePostById(id);
		}
		//return post;
	}
	
	
	
}












