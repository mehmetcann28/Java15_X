package com.mcann.java15_x.service;

import com.mcann.java15_x.dto.request.NewPostRequestDto;
import com.mcann.java15_x.dto.response.AllPostsResponseDto;
import com.mcann.java15_x.entity.Post;
import com.mcann.java15_x.entity.PostState;
import com.mcann.java15_x.exception.ErrorType;
import com.mcann.java15_x.exception.Java15XException;
import com.mcann.java15_x.mapper.PostMapper;
import com.mcann.java15_x.repository.PostRepository;
import com.mcann.java15_x.utility.JwtManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
	private final JwtManager jwtManager;
	private final PostRepository postRepository;
	
	public void newPost(NewPostRequestDto dto) {
		Optional<Long> userId = jwtManager.validateToken(dto.token());
		if (userId.isEmpty()) {
			throw new Java15XException(ErrorType.INVALID_TOKEN);
		}
		Post post = PostMapper.INSTANCE.fromNewPost(dto, userId.get());
//		Post post = Post.builder()
//				.userId(userId.get())
//				.comment(dto.comment())
//				.imageUrl(dto.imageUrl())
//				.commentCount(0)
//				.date(System.currentTimeMillis())
//				.likeCount(0)
//				.state(PostState.ACTIVE)
//				.viewCount(0)
//				.build();
		
		postRepository.save(post);
	}
	
	public List<Post> getAllMyPosts(String token) {
		Optional<Long> userId = jwtManager.validateToken(token);
		if (userId.isEmpty()) {
			throw new Java15XException(ErrorType.INVALID_TOKEN);
		}
		return postRepository.findAllByUserId(userId.get());
	}
	
	public List<AllPostsResponseDto> getAllPosts(String token) {
		Optional<Long> userId = jwtManager.validateToken(token);
		if (userId.isEmpty()) {
			throw new Java15XException(ErrorType.INVALID_TOKEN);
		}
		List<Post> postList = postRepository.findAll();
		/**
		 * postları kısıtlayın. mesela date'e göre son atılmış 10 post
		 * post listesinin içinden userid lerin listelerini çıkartın. List<Long> userids
		 * kullanıcıların listesini Map<Long,User> userList
		 */
		List<AllPostsResponseDto> result = new ArrayList<>();
		postList.forEach(p -> {
			p.getUserId()
		});
		return result;
	}
}