package com.example.iBook.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.iBook.pojo.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
	public List<Post> findAll();
	public Post save(Post post);
	public List<Post> findAllByContentContainingOrderByDateDesc(String content, Pageable page);
	public List<Post> findAllByOrderByDateDesc(Pageable page);

	//public Post deleteById(Post id);
	@Transactional
	@Modifying
	@Query(value = "delete from "
	+ "posts "
	+ "where posts.posts_id = :id", nativeQuery = true)
	public void deletePostById(int id);
	
	
}



