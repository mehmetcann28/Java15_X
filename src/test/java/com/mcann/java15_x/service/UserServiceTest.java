package com.mcann.java15_x.service;

import com.mcann.java15_x.dto.request.RegisterRequestDto;
import com.mcann.java15_x.entity.User;
import com.mcann.java15_x.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {
	
	/**
	 * DİKKAT!!!!
	 * Spring Boot ve Spring projelerinin test işlemleri diğer
	 * test işlemlerinden farklıdır. Çünkü spring boot kendi yapısı
	 * gereği nesneerin oluşturulmasını kendi kontrol eder. Bu nedenle
	 * bir spring boot nesnesini 2 şekilde test edebilirsiniz;
	 * 1- SpringBootTest yapısı ile projeyi ayağa kaldırarak test
	 * 2- Mockito ile mock nesneleri kullanarak test.
	 */
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	
	private RegisterRequestDto dto = new RegisterRequestDto(
			"mehmetcan",
			"Aa123456**",
			"Aa123456**",
			"mehmetcann@gmail.com"
	);
	
	@Test
	@Order(1)
	void successRegister() {
		RegisterRequestDto dto = new RegisterRequestDto(
				"mehmetcan",
				"Aa123456**",
				"Aa123456**",
				"mehmetcann@gmail.com"
				
		);
		Long startCount = userRepository.count();
		userService.register(dto);
		Long endCount = userRepository.count();
		Assertions.assertTrue(startCount+1 == endCount);
	}
	
	@Test
	@Order(2)
	void successRegisterValue(){
		Optional<User> userOptional = userRepository.findOptionalByUserName(dto.userName());
		Assertions.assertTrue(userOptional.isPresent());
		User user = userOptional.get();
		Assertions.assertEquals(dto.userName(), user.getUserName());
		Assertions.assertEquals(dto.password(), user.getPassword());
		Assertions.assertEquals(dto.email(), user.getEmail());
	}
	
	@Test
	@Order(3)
	void exceptionRegister(){
		Exception exception =
				assertThrows(DataIntegrityViolationException.class, () -> userService.register(dto));
		System.out.println("Exception is: " + exception.getMessage());
		Assertions.assertTrue(exception.getMessage().contains("duplicate key value violates unique constraint"));
	}
}