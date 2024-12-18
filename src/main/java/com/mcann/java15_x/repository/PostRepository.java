package com.mcann.java15_x.repository;

import com.mcann.java15_x.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// spring 3.x den sonra repository anatasyonu kullanılmak zorunda değildir.
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllByUserId(Long userId);
	
	List<Post> findTop100ByOrderByDateDesc();
}