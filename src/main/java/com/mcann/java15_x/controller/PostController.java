package com.mcann.java15_x.controller;

import com.mcann.java15_x.dto.request.NewPostRequestDto;
import com.mcann.java15_x.dto.response.BaseResponse;
import com.mcann.java15_x.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}