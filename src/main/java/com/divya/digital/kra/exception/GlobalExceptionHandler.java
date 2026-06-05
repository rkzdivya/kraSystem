package com.divya.digital.kra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.divya.digital.kra.dto.APIResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<APIResponseDTO> handleUserNotFoundException(UserNotFoundException ex) {
        APIResponseDTO response = APIResponseDTO.builder()
                .timeStamp(System.currentTimeMillis())
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    
    
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<APIResponseDTO> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        APIResponseDTO response = APIResponseDTO.builder()
                .timeStamp(System.currentTimeMillis())
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    
    @ExceptionHandler(KraNotFoundException.class)
    public ResponseEntity<APIResponseDTO> handleKraNotFoundException(KraNotFoundException ex) {
        APIResponseDTO response = APIResponseDTO.builder()
                .timeStamp(System.currentTimeMillis())
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<APIResponseDTO> handleFileStorageException(FileStorageException ex) {
        APIResponseDTO response = APIResponseDTO.builder()
                .timeStamp(System.currentTimeMillis())
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<APIResponseDTO> handleUnauthorizedAccessException(FileStorageException ex) {
        APIResponseDTO response = APIResponseDTO.builder()
                .timeStamp(System.currentTimeMillis())
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }
}