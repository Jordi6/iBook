package com.example.iBook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	
	@RequestMapping("/get-posts")
	public List<Post> getPosts() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		User user = UserRepo.findFirstByName(name);
		
		List<Post> posts = (List<Post>) PostRepo.findAll();
		return posts;
	}
	
	@RequestMapping("/get-users")
	public List<User> getUsers() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		User user = UserRepo.findFirstByName(name);
		
		List<User> users = (List<User>) UserRepo.findAll();
		return users;
	}
	
	
	
}






