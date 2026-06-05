package com.divya.digital.kra.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class APIResponseDTO {

	private long timeStamp;
	
	private String message;
	
	private boolean success;
	
	private int status;
	
	private Object data;

}