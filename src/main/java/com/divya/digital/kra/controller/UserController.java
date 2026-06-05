package com.divya.digital.kra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divya.digital.kra.dto.APIResponseDTO;
import com.divya.digital.kra.model.Role;
import com.divya.digital.kra.request.auth.UpdateRequest;
import com.divya.digital.kra.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
    private final UserService userService;

    @GetMapping("/fetchUserByEmail/{email}")
	public ResponseEntity<APIResponseDTO> getUserByEmail(@PathVariable("email") String email) {

		var apiResponse = userService.getUserByUserName(email);
		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
	}
    
    @GetMapping("/{id}")
	public ResponseEntity<APIResponseDTO> getUserById(@PathVariable("id") final Long id) {

		var apiResponse = userService.getUserById(id);
		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));

	}
    
    @GetMapping
	public ResponseEntity<APIResponseDTO> getUsers(@RequestParam(name = "page",defaultValue = "0") final Integer page,
			@RequestParam(name = "size",defaultValue = "10") final Integer size) {
		log.info("getuser");
		var apiResponse = userService.getUsers(page,size);
		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
	}
    
    @PutMapping("/{id}")
	public ResponseEntity<APIResponseDTO> updateUser(@PathVariable("id") final Long id,@Validated
			@RequestBody UpdateRequest request) {

		var apiResponse = userService.updateUser(id, request);
		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
	}
    
   
    
    @DeleteMapping
	public ResponseEntity<APIResponseDTO> deleteUsersById(@RequestBody final List<Long> ids) {

		var apiResponse = userService.deleteUsersById(ids);
		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
	}
    
    
    @GetMapping("/count/{role}")
    public ResponseEntity<APIResponseDTO> getTotalUserCountByRole(@PathVariable("role") final String roleString) {
        Role role = Role.valueOf(roleString.toUpperCase());
        APIResponseDTO apiResponse = userService.countUsersByRole(role);
        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }
    
    @GetMapping("/count")
    public ResponseEntity<APIResponseDTO> getTotalUserCount() {

  		var apiResponse = userService.getTotalUserCount();
  		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
  	}
       
    
   
}
