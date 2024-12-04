package com.mcann.java15_x.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbluser")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String name;
	@Column(unique = true, nullable = false)
	String userName;
	@Column(nullable = false)
	String password;
	String email;
	String avatar;
	String phone;
	String about;
	Integer followCount;
	Integer followingCount;
	ProfileState state;
	Boolean hide;
	Long locationId;
	Long bornDate;
	Long memberDate;
	Long createdDate;
	Long modifiedDate;
}