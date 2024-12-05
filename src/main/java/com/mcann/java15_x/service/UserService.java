package com.mcann.java15_x.service;

import com.mcann.java15_x.dto.request.DologinRequestDto;
import com.mcann.java15_x.dto.request.RegisterRequestDto;
import com.mcann.java15_x.entity.ProfileState;
import com.mcann.java15_x.entity.User;
import com.mcann.java15_x.exception.ErrorType;
import com.mcann.java15_x.exception.Java15XException;
import com.mcann.java15_x.mapper.UserMapper;
import com.mcann.java15_x.repository.UserRepository;
import com.mcann.java15_x.utility.JwtManager;
import com.mcann.java15_x.views.VwUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final JwtManager jwtManager;
	
	public void register(RegisterRequestDto dto) {
		User user = UserMapper.INSTANCE.fromRegisterDto(dto);
		user.setCreatedDate(System.currentTimeMillis());
		user.setMemberDate(System.currentTimeMillis());
		user.setModifiedDate(System.currentTimeMillis());
		user.setState(ProfileState.ACTIVE);
		userRepository.save(user);
	}
	
	public String doLogin(DologinRequestDto dto) {
		// user repo'ya bu kullanıcı adı ve şifre ile bir user var mı diye soruyoruz
		Optional<User> userOptional = userRepository.findOptionalByUserNameAndPassword(dto.userName(), dto.password());
		if (userOptional.isEmpty()) {
			throw new Java15XException(ErrorType.INVALID_USERNAME_OR_PASSWORD);
		}
		String token = jwtManager.createToken(userOptional.get().getId());
		
		return token;
	}
	
	public User getProfile(String token) {
		Optional<Long> userId = jwtManager.validateToken(token);
		if (userId.isEmpty()) { // 1.aşama token kontrolü yapılarak sağlanır
			throw new Java15XException(ErrorType.INVALID_TOKEN);
		}
		Optional<User> userOptional = userRepository.findById(userId.get());
		if (userOptional.isEmpty()) { // 2. aşama böyle bir kullanıcı var mıdır? kontrolü sağlanır
			throw new Java15XException(ErrorType.NOTFOUND_USER);
		}
		return userOptional.get();
	}
	
	public Map<Long,VwUser> findAllByIds(List<Long> userIds) {
		return userRepository.findAllByUserIds(userIds).stream()
				.collect(Collectors.toMap(VwUser::id, vwUser -> vwUser));
	}
	
	public Optional<User> findById(Long userId) {
		return userRepository.findById(userId);
	}
}