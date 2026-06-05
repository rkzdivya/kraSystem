package com.divya.digital.kra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divya.digital.kra.dto.APIResponseDTO;
import com.divya.digital.kra.exception.UserNotFoundException;
import com.divya.digital.kra.request.RefreshTokenRequest;
import com.divya.digital.kra.request.auth.AuthenticationRequest;
import com.divya.digital.kra.request.auth.RegisterRequest;
import com.divya.digital.kra.response.AuthenticationResponse;
import com.divya.digital.kra.response.RefreshTokenResponse;
import com.divya.digital.kra.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authService.authenticate(request));
    }
    
    @PutMapping("/resetpas")
   	public ResponseEntity<APIResponseDTO> resetPassword(@RequestParam String newPassword, @RequestParam String email ){

   		var apiResponse = authService.resetPassword(newPassword,email);
   		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
   	}
    
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));
    }
}