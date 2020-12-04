package com.example.iBook.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.iBook.pojo.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
	public List<Post> findAll();
	public Post save(Post post);
	public List<Post> findAllByContentContainingOrderByDateDesc(String content, Pageable page);
	public List<Post> findAllByOrderByDateDesc(Pageable page);
}
