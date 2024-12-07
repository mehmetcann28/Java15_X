package com.mcann.java15_x.service;

import com.mcann.java15_x.dto.request.RegisterRequestDto;
import com.mcann.java15_x.repository.UserRepository;
import com.mcann.java15_x.utility.JwtManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceMockTest {
	/**
	 * Mock test te gerçek nesnelere ve gerçek davranışlara gerek yoktur.
	 */
	
	@InjectMocks
	private UserService userService;
	@Mock
	private UserRepository userRepository;
	@Mock
	private JwtManager jwtManager;
	
	@Test
	void register(){
		RegisterRequestDto dto = new RegisterRequestDto(
			"mehmetcan",
			"Aa",
			"Aa",
			"mehmetcan@gmail.com"
		);
		userService.register(dto);
	}
	
	@Test
	void demo(){
		List<String> listem = Mockito.mock(ArrayList.class);
		listem.add("mehmetcan");
		listem.add("demet");
		listem.add("güneş");
		Mockito.when(listem.get(Mockito.anyInt())).thenReturn(null);
		System.out.println(listem.get(2000));
	}
}