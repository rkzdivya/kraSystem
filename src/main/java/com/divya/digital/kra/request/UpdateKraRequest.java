package com.divya.digital.kra.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateKraRequest {

	private Long id;
	
	private String kraType;
	
	private String measureOfSuccess;

	private Integer overallWeightage;
		
}
