package com.example.iBook.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.iBook.pojo.Comment;
import com.example.iBook.pojo.Post;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer> {
	public List<Comment> findAll();

	public List<Comment> findAllByPostOrderByDateAsc(Post post);

}
