package com.mcann.java15_x.repository;

import com.mcann.java15_x.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	List<UserRole> findByUserId(Long userId);
}