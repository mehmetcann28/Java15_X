package com.mcann.java15_x.controller;

import com.mcann.java15_x.dto.request.NewPostRequestDto;
import com.mcann.java15_x.dto.response.AllPostsResponseDto;
import com.mcann.java15_x.dto.response.BaseResponse;
import com.mcann.java15_x.entity.Post;
import com.mcann.java15_x.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mcann.java15_x.constant.RestApis.*;

@RestController
@RequestMapping(POST)
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postService;
	
	@PostMapping(NEWPOST)
	public ResponseEntity<BaseResponse<Boolean>> newPost(@RequestBody @Valid NewPostRequestDto dto){
		postService.newPost(dto);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
				                         .code(200)
				                         .success(true)
				                         .data(true)
				                         .message("Yeni post oluşturuldu.")
				                         .build()
		);
	}
	
	@GetMapping(GETALLMYPOSTS)
	public ResponseEntity<BaseResponse<List<Post>>> getAllMyPosts(String token){
		return ResponseEntity.ok(BaseResponse.<List<Post>>builder()
				                         .code(200)
				                         .message("Tüm postlar getirildi")
				                         .data(postService.getAllMyPosts(token))
				                         .success(true)
		                                 .build()
		);
	}
	
	@GetMapping(GETALLPOSTS)
	public ResponseEntity<BaseResponse<List<AllPostsResponseDto>>> getAllPosts(String token){
		return ResponseEntity.ok(BaseResponse.<List<AllPostsResponseDto>>builder()
		                                     .code(200)
		                                     .message("Tüm postlar getirildi")
		                                     .data(postService.getAllPosts(token))
		                                     .success(true)
		                                     .build()
		);
	}
}