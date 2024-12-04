package com.mcann.java15_x.repository;

import com.mcann.java15_x.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findOptionalByUserNameAndPassword(String username, String password);
}