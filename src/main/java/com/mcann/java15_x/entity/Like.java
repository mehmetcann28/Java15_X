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
@Table(name = "tbllike")
public class Like {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Long postId;
	Long userId;
	LikeState state;
}