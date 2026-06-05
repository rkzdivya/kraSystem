package com.divya.digital.kra.response;

import com.divya.digital.kra.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
	private String token;
	private String message;
	private UserDTO user;
	private String refreshToken;
}