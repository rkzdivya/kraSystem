package com.divya.digital.kra.service;

import java.util.List;

import com.divya.digital.kra.dto.APIResponseDTO;
import com.divya.digital.kra.model.Role;
import com.divya.digital.kra.model.User;
import com.divya.digital.kra.request.auth.RegisterRequest;
import com.divya.digital.kra.request.auth.UpdateRequest;

public interface UserService {

	List<User> getAllUsers();

	APIResponseDTO getUserByUserName(String email);

	APIResponseDTO getUsers(Integer page, Integer size);

	APIResponseDTO updateUser(Long id, UpdateRequest request);

	APIResponseDTO getUserById(Long id);

	APIResponseDTO resetPassword(String newPassword, String email);

	APIResponseDTO deleteUsersById(List<Long> ids);

	APIResponseDTO getTotalUserCount();

	APIResponseDTO countUsersByRole(Role role);


   

	
}
