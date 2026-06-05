package com.divya.digital.kra.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.divya.digital.kra.dto.APIResponseDTO;
import com.divya.digital.kra.dto.UserDTO;
import com.divya.digital.kra.exception.UserNotFoundException;
import com.divya.digital.kra.model.Role;
import com.divya.digital.kra.model.User;
import com.divya.digital.kra.repository.UserRepository;
import com.divya.digital.kra.request.auth.RegisterRequest;
import com.divya.digital.kra.request.auth.UpdateRequest;
import com.divya.digital.kra.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public APIResponseDTO getUserByUserName(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException("User not found with email: " + email);
		}

		User user = optionalUser.get();
		UserDTO userDTO = UserDTO.convertToDto(user);

		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).data(userDTO).message("User found")
				.success(true).status(HttpStatus.OK.value()).build();
	}

	@Override
	public APIResponseDTO getUsers(Integer page, Integer size) {
		Sort sort = Sort.by(Sort.Direction.DESC, "updatedAt");
		Page<User> listOfUser = userRepository.findAll(PageRequest.of(page, size, sort));
		List<UserDTO> listDTO = new ArrayList<>();

		if (listOfUser.isEmpty()) {
			throw new UserNotFoundException("Data not found");
		} else {
			for (User user : listOfUser.getContent()) {
				UserDTO userDto = UserDTO.convertToDto(user);
				listDTO.add(userDto);
			}
		}

		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).data(listDTO).message("data found")
				.success(true).status(HttpStatus.OK.value()).build();
	}

	@Override
	public APIResponseDTO updateUser(Long id, UpdateRequest request) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException("User not found with id: " + id);
		}
		var role = request.getRole() == 1 ? Role.ADMIN : request.getRole() == 2 ? Role.MANAGER : Role.EMPLOYEE;
		User user = optionalUser.get();
		user.setName(request.getName());
		user.setLastname(request.getLastname());
		user.setPassword(request.getPassword()); // You might want to hash the password before saving
		user.setRole(role);
		user.setPhonenumber(request.getPhonenumber());
		user.setUpdatedAt(LocalDateTime.now());
		user.setActive(request.isActive());

		userRepository.save(user);

		UserDTO userDTO = UserDTO.convertToDto(user);

		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).data(userDTO)
				.message("User updated successfully").success(true).status(HttpStatus.OK.value()).build();
	}

	@Override
	public APIResponseDTO getUserById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
	        throw new UserNotFoundException("User not found with id: " + id);
	    }

		User user = optionalUser.get();
		UserDTO userDTO = UserDTO.convertToDto(user);

		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).data(userDTO).message("User found")
				.success(true).status(HttpStatus.OK.value()).build();
	}
	
	


	@Override
	public APIResponseDTO resetPassword(String newPassword, String username) {
		Optional<User> optionalUser = userRepository.findByEmail(username);
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException("User not found with username: " + username);
		}

		User user = optionalUser.get();
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);

		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("Password reset successfully")
				.success(true).status(HttpStatus.OK.value()).build();
	}

	@Override
	public APIResponseDTO deleteUsersById(List<Long> ids) {
		  List<Long> notFoundIds = new ArrayList<>();
		    List<User> usersToDelete = new ArrayList<>();

		    for (Long id : ids) {
		        Optional<User> optionalUser = userRepository.findById(id);
		        if (optionalUser.isPresent()) {
		            usersToDelete.add(optionalUser.get());
		        } else {
		            notFoundIds.add(id);
		        }
		    }

		    if (!notFoundIds.isEmpty()) {
		    	
		        return APIResponseDTO.builder()
		                .timeStamp(System.currentTimeMillis())
		                .message("Users not found with ids: " + notFoundIds)
		                .success(false)
		                .status(HttpStatus.NOT_FOUND.value())
		                .data(notFoundIds)
		                .build();
		    }

		    userRepository.deleteAll(usersToDelete);

		    return APIResponseDTO.builder()
		            .timeStamp(System.currentTimeMillis())
		            .message("Users deleted successfully")
		            .success(true)
		            .status(HttpStatus.OK.value())
		            .build();
		}

	@Override
	public APIResponseDTO getTotalUserCount() {
		 long employeeCount = userRepository.count();
		 return APIResponseDTO.builder()
                 .timeStamp(System.currentTimeMillis())
                 .data(employeeCount)
                 .message("Total number of users are "+ employeeCount)
                 .success(true)
                 .status(HttpStatus.OK.value())
                 .build();
		}

	@Override
	public APIResponseDTO countUsersByRole(Role role) {
		 long employeeCount = userRepository.countUsersByRole(role);
		 return APIResponseDTO.builder()
                 .timeStamp(System.currentTimeMillis())
                 .data(employeeCount)
                 .message("Total number of users with role "+ role)
                 .success(true)
                 .status(HttpStatus.OK.value())
                 .build();
		}

		 
}
