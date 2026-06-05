package com.divya.digital.kra.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.divya.digital.kra.dto.APIResponseDTO;
import com.divya.digital.kra.dto.KraCategoryDto;
import com.divya.digital.kra.dto.KraFieldDto;
import com.divya.digital.kra.dto.KraFormDto;
import com.divya.digital.kra.exception.KraNotFoundException;
import com.divya.digital.kra.exception.UnauthorizedAccessException;
import com.divya.digital.kra.exception.UserNotFoundException;
import com.divya.digital.kra.model.KraCategory;
import com.divya.digital.kra.model.KraField;
import com.divya.digital.kra.model.KraForm;
import com.divya.digital.kra.model.User;
import com.divya.digital.kra.repository.KraFormRepository;
import com.divya.digital.kra.repository.UserRepository;
import com.divya.digital.kra.service.KraFormService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KraFormServiceImpl implements KraFormService {

	@Autowired
	private KraFormRepository kraFormRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjectMapper objectMapper; // For JSON conversion

	public APIResponseDTO createKraForm(Long userId, KraFormDto kraFormDTO) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

		log.info("Validating Kra Form overall weightage");
		if (!kraFormDTO.validate()) {
			log.debug("KRA form validation failed");
			return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("KRA form validation failed")
					.data(null).success(false).status(HttpStatus.BAD_REQUEST.value()).build();
		}

		KraForm kraForm = KraForm.builder().user(user).kraType(kraFormDTO.getKraType())
				.overallWeightage(kraFormDTO.getOverallWeightage()) // Corrected from overallWeightage() to
																	// overallWeightageform()
				.status(kraFormDTO.getStatus()).build();

		List<KraCategory> categories = kraFormDTO.getCategories().stream().map(kraCategoryDTO -> {
			KraCategory kraCategory = KraCategory.builder().kraName(kraCategoryDTO.getKraName())
					.overallWeightage(kraCategoryDTO.getOverallWeightage()).detailedKra(kraCategoryDTO.getDetailedKra())
					.build();

			List<KraField> fields = kraCategoryDTO.getFields().stream()
					.map(fieldDTO -> KraField.builder().weightage(fieldDTO.getWeightage())
							.measureOfSuccess(fieldDTO.getMeasureOfSuccess())
							.individualProgressTrackingIndicators(fieldDTO.getIndividualProgressTrackingIndicators())
							.build())
					.toList();

			kraCategory.setFields(fields);

			return kraCategory;
		}).toList();

		kraForm.setCategories(categories);

		kraFormRepository.save(kraForm);

		log.debug("KRA uploaded successfully");
		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("KRA created successfully")
				.data(kraForm).success(true).status(HttpStatus.CREATED.value()).build();
	}

	@Override
	public APIResponseDTO getKraForm(Long id) {
	    Optional<KraForm> optionalKraForm = kraFormRepository.findById(id);
	    
	    if (optionalKraForm.isPresent()) {
	        KraForm kraForm = optionalKraForm.get();
	        return APIResponseDTO.builder()
	                .timeStamp(System.currentTimeMillis())
	                .message("KRA retrieved successfully")
	                .data(kraForm)
	                .success(true)
	                .status(HttpStatus.OK.value())
	                .build();
	    } else {
	        return APIResponseDTO.builder()
	                .timeStamp(System.currentTimeMillis())
	                .message("KRA not found with id " + id)
	                .success(false)
	                .status(HttpStatus.NOT_FOUND.value())
	                .build();
	    }
	}


	public APIResponseDTO submitKraForm(Long id) {
	    KraForm kraForm = kraFormRepository.findById(id).orElse(null);
	    
	    if (kraForm == null) {
	        return APIResponseDTO.builder()
	                .timeStamp(System.currentTimeMillis())
	                .message("KRA Form not found")
	                .success(false)
	                .status(HttpStatus.NOT_FOUND.value())
	                .build();
	    } else {
	        if ("submitted".equalsIgnoreCase(kraForm.getStatus())) {
	            return APIResponseDTO.builder()
	                    .timeStamp(System.currentTimeMillis())
	                    .message("KRA Form is already submitted")
	                    .success(false)
	                    .status(HttpStatus.BAD_REQUEST.value())
	                    .build();
	        } else {
	            kraForm.setStatus("submitted");
	            kraFormRepository.save(kraForm);
	            return APIResponseDTO.builder()
	                    .timeStamp(System.currentTimeMillis())
	                    .message("KRA submitted successfully")
	                    .data(kraForm)
	                    .success(true)
	                    .status(HttpStatus.CREATED.value())
	                    .build();
	        }
	    }
	}


	public APIResponseDTO approveKraForm(Long id) {
	    Optional<KraForm> optionalKraForm = kraFormRepository.findById(id);
	    
	    if (optionalKraForm.isPresent()) {
	        KraForm kraForm = optionalKraForm.get();
	        kraForm.setStatus("approved");
	        kraFormRepository.save(kraForm);
	        return APIResponseDTO.builder()
	                .timeStamp(System.currentTimeMillis())
	                .message("KRA approved successfully")
	                .data(kraForm)
	                .success(true)
	                .status(HttpStatus.OK.value())
	                .build();
	    } else {
	        return APIResponseDTO.builder()
	                .timeStamp(System.currentTimeMillis())
	                .message("KRA Form not found")
	                .success(false)
	                .status(HttpStatus.NOT_FOUND.value())
	                .build();
	    }
	}


	public APIResponseDTO rejectKraForm(Long id) {
	    Optional<KraForm> optionalKraForm = kraFormRepository.findById(id);
	    
	    if (optionalKraForm.isPresent()) {
	        KraForm kraForm = optionalKraForm.get();
	        kraForm.setStatus("rejected");
	        kraFormRepository.save(kraForm);
	        return APIResponseDTO.builder()
	                .timeStamp(System.currentTimeMillis())
	                .message("KRA rejected successfully")
	                .data(kraForm)
	                .success(true)
	                .status(HttpStatus.OK.value())
	                .build();
	    } else {
	        return APIResponseDTO.builder()
	                .timeStamp(System.currentTimeMillis())
	                .message("KRA Form not found")
	                .success(false)
	                .status(HttpStatus.NOT_FOUND.value())
	                .build();
	    }
	}


	@Override
	public APIResponseDTO updateKraForm(Long id, KraFormDto kraFormDTO) {
		KraForm kraForm = kraFormRepository.findById(id)
				.orElseThrow(() -> new KraNotFoundException("KRA Form not found"));

		log.info("checking Only the user who created the form can update it");
		if (!kraForm.getUser().getId().equals(kraFormDTO.getUserId())) {
			throw new UnauthorizedAccessException("Unauthorized access");
		}

		log.info("Validating Kra Form overall weightage");
		if (!kraFormDTO.validate()) {
			log.debug("KRA form validation failed");
			return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("KRA form validation failed")
					.data(null).success(false).status(HttpStatus.BAD_REQUEST.value()).build();
		}

		kraForm.setKraType(kraFormDTO.getKraType());
		kraForm.setOverallWeightage(kraFormDTO.getOverallWeightage());
		kraForm.setStatus(kraFormDTO.getStatus());

		kraForm.getCategories().clear();

		for (KraCategoryDto kraCategoryDTO : kraFormDTO.getCategories()) {
			KraCategory kraCategory = new KraCategory();
			kraCategory.setKraName(kraCategoryDTO.getKraName());
			kraCategory.setOverallWeightage(kraCategoryDTO.getOverallWeightage());
			kraCategory.setDetailedKra(kraCategoryDTO.getDetailedKra());
			kraCategory.setFields(new ArrayList<>());

			for (KraFieldDto fieldDTO : kraCategoryDTO.getFields()) {
				KraField kraField = new KraField();
				kraField.setWeightage(fieldDTO.getWeightage());
				kraField.setMeasureOfSuccess(fieldDTO.getMeasureOfSuccess());
				kraField.setIndividualProgressTrackingIndicators(fieldDTO.getIndividualProgressTrackingIndicators());
				kraCategory.getFields().add(kraField);
			}

			kraForm.getCategories().add(kraCategory);
		}

		kraFormRepository.save(kraForm);

		log.debug("KRA updated successfully");
		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("KRA updated successfully")
				.data(kraForm).success(true).status(HttpStatus.OK.value()).build();
	}

	public APIResponseDTO uploadKraForm(Long userId, MultipartFile file) throws IOException {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

		KraFormDto kraFormDTO;
		try (InputStreamReader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)) {
			kraFormDTO = objectMapper.readValue(reader, KraFormDto.class);
		} catch (IOException e) {
			log.error("Error reading the KRA form file", e);
			return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("Invalid file format")
					.data(null).success(false).status(HttpStatus.BAD_REQUEST.value()).build();
		}

		log.info("Validating Kra Form overall weightage");
		if (!kraFormDTO.validate()) {
			log.debug("KRA form validation failed");
			return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("KRA form validation failed")
					.data(null).success(false).status(HttpStatus.BAD_REQUEST.value()).build();
		}

		KraForm kraForm = KraForm.builder().user(user).kraType(kraFormDTO.getKraType())
				.overallWeightage(kraFormDTO.getOverallWeightage()).status(kraFormDTO.getStatus())
				.fileName(file.getOriginalFilename()).fileType(file.getContentType()).fileData(file.getBytes()).build();

		List<KraCategory> categories = kraFormDTO.getCategories().stream().map(kraCategoryDTO -> {
			KraCategory kraCategory = KraCategory.builder().kraName(kraCategoryDTO.getKraName())
					.overallWeightage(kraCategoryDTO.getOverallWeightage()).detailedKra(kraCategoryDTO.getDetailedKra())
					.build();

			List<KraField> fields = kraCategoryDTO.getFields().stream()
					.map(fieldDTO -> KraField.builder().weightage(fieldDTO.getWeightage())
							.measureOfSuccess(fieldDTO.getMeasureOfSuccess())
							.individualProgressTrackingIndicators(fieldDTO.getIndividualProgressTrackingIndicators())
							.build())
					.toList();

			kraCategory.setFields(fields);

			return kraCategory;
		}).toList();

		kraForm.setCategories(categories);

		kraFormRepository.save(kraForm);

		log.debug("KRA uploaded successfully");
		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("KRA created successfully")
				.data(kraForm).success(true).status(HttpStatus.CREATED.value()).build();
	}

	public APIResponseDTO updateKraForm(Long id, MultipartFile file) throws IOException {
		KraForm kraForm = kraFormRepository.findById(id)
				.orElseThrow(() -> new KraNotFoundException("KRA Form not found"));

		KraFormDto kraFormDTO;
		try (InputStreamReader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)) {
			kraFormDTO = objectMapper.readValue(reader, KraFormDto.class);
		} catch (IOException e) {
			log.error("Error reading the KRA form file", e);
			return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("Invalid file format")
					.data(null).success(false).status(HttpStatus.BAD_REQUEST.value()).build();
		}

		log.info("Validating Kra Form overall weightage");
		if (!kraFormDTO.validate()) {
			log.debug("KRA form validation failed");
			return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("KRA form validation failed")
					.data(null).success(false).status(HttpStatus.BAD_REQUEST.value()).build();
		}

		kraForm.setKraType(kraFormDTO.getKraType());
		kraForm.setOverallWeightage(kraFormDTO.getOverallWeightage());
		kraForm.setStatus(kraFormDTO.getStatus());
		kraForm.setFileName(file.getOriginalFilename());
		kraForm.setFileType(file.getContentType());
		kraForm.setFileData(file.getBytes());

		kraForm.getCategories().clear();

		for (KraCategoryDto kraCategoryDTO : kraFormDTO.getCategories()) {
			KraCategory kraCategory = new KraCategory();
			kraCategory.setKraName(kraCategoryDTO.getKraName());
			kraCategory.setOverallWeightage(kraCategoryDTO.getOverallWeightage());
			kraCategory.setDetailedKra(kraCategoryDTO.getDetailedKra());
			kraCategory.setFields(new ArrayList<>());

			for (KraFieldDto fieldDTO : kraCategoryDTO.getFields()) {
				KraField kraField = new KraField();
				kraField.setWeightage(fieldDTO.getWeightage());
				kraField.setMeasureOfSuccess(fieldDTO.getMeasureOfSuccess());
				kraField.setIndividualProgressTrackingIndicators(fieldDTO.getIndividualProgressTrackingIndicators());
				kraCategory.getFields().add(kraField);
			}

			kraForm.getCategories().add(kraCategory);
		}

		kraFormRepository.save(kraForm);

		log.debug("KRA updated successfully");
		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("KRA updated successfully")
				.data(kraForm).success(true).status(HttpStatus.OK.value()).build();
	}

	public Resource downloadKraForm(Long id) {
		KraForm kraForm = kraFormRepository.findById(id)
				.orElseThrow(() -> new KraNotFoundException("KRA Form not found"));

		KraFormDto kraFormDto = KraFormDto.builder().userId(kraForm.getUser().getId()).kraType(kraForm.getKraType())
				.overallWeightage(kraForm.getOverallWeightage()).status(kraForm.getStatus())
				.categories(kraForm.getCategories().stream()
						.map(category -> KraCategoryDto.builder().kraName(category.getKraName())
								.overallWeightage(category.getOverallWeightage()).detailedKra(category.getDetailedKra())
								.fields(category.getFields().stream()
										.map(field -> KraFieldDto.builder().weightage(field.getWeightage())
												.measureOfSuccess(field.getMeasureOfSuccess())
												.individualProgressTrackingIndicators(
														field.getIndividualProgressTrackingIndicators())
												.build())
										.collect(Collectors.toList()))
								.build())
						.collect(Collectors.toList()))
				.build();

		try {
			String jsonString = objectMapper.writeValueAsString(kraFormDto);
			return new InputStreamResource(new ByteArrayInputStream(jsonString.getBytes()));
		} catch (JsonProcessingException e) {
			log.error("Error converting KRA form to JSON", e);
			throw new RuntimeException("Error generating file");
		}
	}

	@Override
	public APIResponseDTO pendingKraForm(Long id) {
	    Optional<KraForm> optionalKraForm = kraFormRepository.findById(id);

	    if (optionalKraForm.isPresent()) {
	        KraForm kraForm = optionalKraForm.get();
	        kraForm.setStatus("pending");
	        kraFormRepository.save(kraForm);
	        return APIResponseDTO.builder()
	                .timeStamp(System.currentTimeMillis())
	                .message("KRA set to pending state successfully")
	                .data(kraForm)
	                .success(true)
	                .status(HttpStatus.OK.value())
	                .build();
	    } else {
	        return APIResponseDTO.builder()
	                .timeStamp(System.currentTimeMillis())
	                .message("KRA Form not found")
	                .success(false)
	                .status(HttpStatus.NOT_FOUND.value())
	                .build();
	    }
	}

	@Override
	public APIResponseDTO saveComment(Long userId, Long kraFormId, String managersComment) {
	    log.info("Adding Manager's comment");

	    Optional<KraForm> optionalKraForm = kraFormRepository.findById(kraFormId);

	    if (optionalKraForm.isPresent()) {
	        KraForm kraForm = optionalKraForm.get();
	        kraForm.setManagersComment(managersComment);
	        kraFormRepository.save(kraForm);
	        log.info("Manager's comment added successfully");
	        return APIResponseDTO.builder()
	                .timeStamp(System.currentTimeMillis())
	                .message("Manager's comment added successfully")
	                .success(true)
	                .status(HttpStatus.OK.value())
	                .build();
	    } else {
	        log.error("KRA Form not found");
	        return APIResponseDTO.builder()
	                .timeStamp(System.currentTimeMillis())
	                .message("KRA Form not found")
	                .success(false)
	                .status(HttpStatus.NOT_FOUND.value())
	                .build();
	    }
	}


}
