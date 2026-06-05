package com.divya.digital.kra.dto;

import com.divya.digital.kra.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class UserDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String role;
	private String phoneNumber;
	private boolean isActive;

	public static UserDTO convertToDto(User user) {
		return new UserDTO(user.getId(), user.getName(), user.getLastname(), user.getUsername(), user.getEmail(),
				user.getRole().name(), user.getPhonenumber(),user.isActive());

	}
}
