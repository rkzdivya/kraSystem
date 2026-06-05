//package com.divya.digital.kra.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.divya.digital.kra.dto.APIResponseDTO;
//import com.divya.digital.kra.request.CreateKraRequest;
//import com.divya.digital.kra.request.UpdateKraRequest;
//import com.divya.digital.kra.service.KraService;
//import com.divya.digital.kra.service.StorageService;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@RequestMapping("api/v1/kra")
//@RestController
//@CrossOrigin(origins = "*")
//public class KraController {
//
//	@Autowired
//	private KraService kraService;
//
//	@Autowired
//	private StorageService storageService;
//
//	@PostMapping
//	public ResponseEntity<APIResponseDTO> addKra(@Validated @RequestBody CreateKraRequest createKRARequest) {
//
//		var apiResponse = kraService.uploadKra(createKRARequest);
//		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
//	}
//
//	@GetMapping("/{email}")
//	public ResponseEntity<APIResponseDTO> getKra(@PathVariable String email) {
//
//		var apiResponse = kraService.getKra(email);
//		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
//	}
//
//	@PutMapping
//	public ResponseEntity<APIResponseDTO> updateKra(@Validated @RequestBody UpdateKraRequest updateKraRequest) {
//
//		var apiResponse = kraService.updateKra(updateKraRequest);
//		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
//	}
//
//	@GetMapping("/RequestedKraCount")
//	public ResponseEntity<APIResponseDTO> getTotalRequestedKraCount() {
//
//		var apiResponse = kraService.getTotalRequestedKraCount();
//		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
//	}
//
//	@PostMapping("/upload")
//	public ResponseEntity<APIResponseDTO> uploadKra(@RequestParam("file") MultipartFile file) {
//		String message;
//		try {
//			storageService.save(file);
//			message = "Uploaded the file successfully: " + file.getOriginalFilename();
//			return ResponseEntity.ok().body(APIResponseDTO.builder().timeStamp(System.currentTimeMillis())
//					.message(message).success(true).status(HttpStatus.OK.value()).build());
//		} catch (Exception e) {
//			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
//					.body(APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message(message).success(false)
//							.status(HttpStatus.EXPECTATION_FAILED.value()).build());
//		}
//	}
//
//	@GetMapping("/download/{filename:.+}")
//	public ResponseEntity<Resource> downloadKra(@PathVariable String filename) {
//		Resource file = storageService.load(filename);
//		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//				.body(file);
//	}
//
//}
