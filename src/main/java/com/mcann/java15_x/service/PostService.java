package com.mcann.java15_x.service;

import com.mcann.java15_x.dto.request.NewPostRequestDto;
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
}