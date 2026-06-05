package com.divya.digital.kra.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.divya.digital.kra.dto.APIResponseDTO;
import com.divya.digital.kra.dto.KraFormDto;
import com.divya.digital.kra.exception.KraNotFoundException;
import com.divya.digital.kra.model.KraForm;
import com.divya.digital.kra.model.User;
import com.divya.digital.kra.repository.KraFormRepository;
import com.divya.digital.kra.service.EmailService;
import com.divya.digital.kra.service.KraFormService;
import com.divya.digital.kra.service.NotificationService;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/kra/form")
@Slf4j
public class KraFormController {

    @Autowired
    private KraFormService kraFormService;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private KraFormRepository kraFormRepository;
    
    @PostMapping
    public ResponseEntity<APIResponseDTO> createKraForm(Authentication authentication, @RequestBody KraFormDto kraFormDTO) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        var apiResponse = kraFormService.createKraForm(userId, kraFormDTO);
        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }
    
    @PostMapping("/comment/{kraFormId}")
    public ResponseEntity<APIResponseDTO> addManagersComment(
            Authentication authentication, 
            @PathVariable Long kraFormId, 
            @RequestBody String managersComment) {
        
        Long userId = ((User) authentication.getPrincipal()).getId();
        var apiResponse = kraFormService.saveComment(userId, kraFormId, managersComment);
        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<APIResponseDTO> getKraForm(@PathVariable Long id) {
    	var apiResponse = kraFormService.getKraForm(id);
    	return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<APIResponseDTO> updateKraForm(Authentication authentication, @PathVariable Long id, @RequestBody KraFormDto
    		kraFormDTO) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        kraFormDTO.setUserId(userId);
        var apiResponse = kraFormService.updateKraForm(id, kraFormDTO);
        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }

    @PutMapping("/{id}/submit")
    public ResponseEntity<APIResponseDTO> submitKraForm(@PathVariable Long id) {
    	var apiResponse = kraFormService.submitKraForm(id);
    	return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<APIResponseDTO> approveKraForm(@PathVariable Long id) {
    	var apiResponse = kraFormService.approveKraForm(id);
    	return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<APIResponseDTO> rejectKraForm(@PathVariable Long id) {
    	var apiResponse = kraFormService.rejectKraForm(id);
    	return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }
    
    @PutMapping("/{id}/pending")
    public ResponseEntity<APIResponseDTO> pendingKraForm(@PathVariable Long id) {
    	var apiResponse = kraFormService.pendingKraForm(id);
    	return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }
    
    @PostMapping("/upload")
    public ResponseEntity<APIResponseDTO> uploadKraForm(@RequestParam Long userId, @RequestParam("file") MultipartFile file) throws IOException {
        APIResponseDTO response = kraFormService.uploadKraForm(userId, file);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<APIResponseDTO> updateKraForm(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        APIResponseDTO response = kraFormService.updateKraForm(id, file);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadKraForm(@PathVariable Long id) {
        Resource resource = kraFormService.downloadKraForm(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"kra_form_" + id + ".json\"")
                .body(resource);
    }
    
    @PostMapping("/sendKraFormEmail")
    public ResponseEntity<String> sendKraFormEmail(@RequestParam Long kraFormId, @RequestParam String toEmail) {
        KraForm kraForm = kraFormRepository.findById(kraFormId).orElseThrow(() -> new KraNotFoundException("KRA Form not found"));

        try {
        	log.info("Creating a temporary file");
            Path tempFilePath = Files.createTempFile("kraForm", ".pdf");
            File tempFile = tempFilePath.toFile();
            log.info("temporary file created");
            
            log.info("Writing the file data to the temporary file");
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(kraForm.getFileData());
            }

            log.info("Sending the email with the attachment");
            emailService.sendEmailWithAttachment(toEmail, "KRA Form", "Please find the attached KRA form.", tempFile);

            log.info("Deleting the temporary file");
            Files.delete(tempFilePath);

            return ResponseEntity.ok("KRA form sent successfully");
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error sending email: " + e.getMessage());
        }
    }
    
    
    @PostMapping("/send")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) {
        emailService.sendSimpleMessage(to, subject, text);
        return "Email sent successfully";
    }
    


}