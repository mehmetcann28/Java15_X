package com.mcann.java15_x.service;

import com.mcann.java15_x.dto.request.NewPostRequestDto;
import com.mcann.java15_x.dto.response.AllPostsResponseDto;
import com.mcann.java15_x.entity.Post;
import com.mcann.java15_x.exception.ErrorType;
import com.mcann.java15_x.exception.Java15XException;
import com.mcann.java15_x.mapper.PostMapper;
import com.mcann.java15_x.repository.PostRepository;
import com.mcann.java15_x.utility.JwtManager;
import com.mcann.java15_x.views.VwUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostService {
	private final JwtManager jwtManager;
	private final UserService userService;
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
		Long start = System.nanoTime();
		Optional<Long> userId = jwtManager.validateToken(token);
		if (userId.isEmpty()) {
			throw new Java15XException(ErrorType.INVALID_TOKEN);
		}
		Long postListStart = System.nanoTime();
		List<Post> postList = postRepository.findTop100ByOrderByDateDesc(); // 0-3ms
		Long postListEnd = System.nanoTime();
		log.info("db post request...: " + (postListEnd - postListStart) + "ns");
		log.info("db post request...: " + (postListEnd - postListStart) / 1_000_000 + "ms");
		/**
		 * postları kısıtlayın. mesela date'e göre son atılmış 10 post
		 * post listesinin içinden userid lerin listelerini çıkartın. List<Long> userids
		 * kullanıcıların listesini Map<Long,User> userList
		 */
		/**
		 * Diyelim ki 3.000.000.000 işlem gücü -> 100 data
		 * 30.000.000 saniye de
		 * 30.000 -> 1/30.000ms de 100 datayı işleyebilir.
		 */
		List<Long> userIds = postList.stream().map(Post::getUserId).toList();// ms altındadır.
		Long userListStart = System.nanoTime();
		Map<Long,VwUser> userList = userService.findAllByIds(userIds); // 0-2ms
		Long userListEnd = System.nanoTime();
		log.info("db user request...: " + (userListEnd - userListStart) + "ns");
		log.info("db user request...: " + (postListEnd - postListStart) / 1_000_000 + "ms");
		List<AllPostsResponseDto> result = new ArrayList<>();
		postList.forEach(p -> {
			VwUser user = userList.get(p.getUserId());
			AllPostsResponseDto newDto =
					PostMapper.INSTANCE.fromPostAndUser(p, user.userName(), user.name(), user.avatar());
			result.add(newDto);
		});// 0-1ms
		Long end = System.nanoTime();
		log.info("getAllPosts took " + (end - start) + "ns");
		log.info("getAllPosts took " + (end - start) / 1_000_000 + "ms");
		return result;
	}
}