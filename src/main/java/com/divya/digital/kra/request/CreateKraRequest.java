package com.divya.digital.kra.request;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateKraRequest {

	private String email;

	private String kraType;

	private String measureOfSuccess;

}
