package com.divya.digital.kra.request;

import java.time.LocalDateTime;
import java.util.List;

import com.divya.digital.kra.dto.KraCategoryDto;

import lombok.Data;

@Data
public class saveKraRequest {
	private Long userId;
	private String userEmail;
	private String kraType;
	private Integer overallWeightage;
	private String status;
	private List<KraCategoryDto> categories;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
