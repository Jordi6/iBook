package com.example.iBook.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.iBook.pojo.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
	public List<Post> findAll();
}
