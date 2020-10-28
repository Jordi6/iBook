package com.example.iBook.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.iBook.pojo.Comment;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer> {
	public List<Comment> findAll();
}
