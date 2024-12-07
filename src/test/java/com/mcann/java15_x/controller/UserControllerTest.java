package com.mcann.java15_x.controller;

import com.mcann.java15_x.dto.request.DologinRequestDto;
import com.mcann.java15_x.dto.request.RegisterRequestDto;
import com.mcann.java15_x.dto.response.BaseResponse;
import com.mcann.java15_x.exception.ErrorType;
import com.mcann.java15_x.exception.Java15XException;
import com.mcann.java15_x.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserControllerTest {

	@Autowired
	private UserController userController;
	@Autowired
	private UserService userService;
	
	@Test
	void exceptionRegisterInvalidValues() {
		RegisterRequestDto dto = new RegisterRequestDto(
				"demet",
				"Aa12345",
				"Aa12",
				"demet@gmail.com"
		);
		Java15XException exception = Assertions.assertThrows(Java15XException.class, ()-> userController.register(dto));
		Assertions.assertEquals(ErrorType.PASSWORD_ERROR.getMessage(), exception.getMessage());
	}
	
	@Test
	void successRegister() {
		RegisterRequestDto dto = new RegisterRequestDto(
				"mehmetcan",
				"Aa123456**",
				"Aa123456**",
				"mehmetcan@gmail.com"
		);
		BaseResponse<Boolean> base = userController.register(dto).getBody();
		Assertions.assertTrue(base.getSuccess());
	}
	
	@Test
	void successLogin() {
		DologinRequestDto dto = new DologinRequestDto(
				"mehmetcan",
				"Aa123456**"
		);
		BaseResponse<String> base = userController.doLogin(dto).getBody();
		Assertions.assertTrue(base.getSuccess());
		String token = base.getData();
		Assertions.assertNotNull(token);
		Assertions.assertNotEquals("", token);
	}
	
	@Test
	void exceptionLoginInvalidUserNameOrPassword() {
		DologinRequestDto dto = new DologinRequestDto(
				"mehmetcan",
				"Aa123456"
		);
		Java15XException exception = Assertions.assertThrows(Java15XException.class,()->userController.doLogin(dto));
		Assertions.assertEquals(ErrorType.INVALID_USERNAME_OR_PASSWORD.getMessage(), exception.getMessage());
	}
}