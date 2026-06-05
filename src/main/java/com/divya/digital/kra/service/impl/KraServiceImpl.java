//package com.divya.digital.kra.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//import com.divya.digital.kra.dto.APIResponseDTO;
//import com.divya.digital.kra.exception.KraNotFoundException;
//import com.divya.digital.kra.model.Kra;
//import com.divya.digital.kra.repository.KraRepository;
//import com.divya.digital.kra.request.CreateKraRequest;
//import com.divya.digital.kra.request.UpdateKraRequest;
//import com.divya.digital.kra.service.KraService;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class KraServiceImpl implements KraService {
//
//	@Autowired
//	private final KraRepository kraRepository;
//
//	@Override
//	public APIResponseDTO uploadKra(CreateKraRequest createKraForm) {
//		log.info("Uploading KRA");
//
//		Kra kra = new Kra();
//		kra.setEmail(createKraForm.getEmail());
//		kra.setKraType(createKraForm.getKraType());
//		kra.setMeasureOfSuccess(createKraForm.getMeasureOfSuccess());
//		kraRepository.save(kra);
//
//		log.debug("KRA uploaded successfully");
//		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("KRA created successfully")
//				.success(true).status(HttpStatus.CREATED.value()).build();
//	}
//
//	@Override
//	public APIResponseDTO getKra(String email) {
//		log.info("Fetching KRA for user: {}", email);
//		Kra kra = kraRepository.findByEmail(email)
//				.orElseThrow(() -> new KraNotFoundException("KRA not found for user: " + email));
//
//		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("KRA fetched successfully")
//				.success(true).status(HttpStatus.OK.value()).data(kra).build();
//	}
//
//	@Override
//	public APIResponseDTO updateKra(UpdateKraRequest updateKraForm) {
//		log.info("Updating KRA");
//
//		Kra kra = kraRepository.findById(updateKraForm.getId())
//				.orElseThrow(() -> new KraNotFoundException("KRA not found with id: " + updateKraForm.getId()));
//
//		kra.setKraType(updateKraForm.getKraType());
//		kra.setMeasureOfSuccess(updateKraForm.getMeasureOfSuccess());
//		kra.setOverallWeightage(updateKraForm.getOverallWeightage());
//
//		kraRepository.save(kra);
//
//		log.debug("KRA updated successfully");
//		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("KRA updated successfully")
//				.success(true).status(HttpStatus.OK.value()).build();
//	}
//
//	@Override
//	public APIResponseDTO getTotalRequestedKraCount() {
//	  log.info("counting total KRA");
//      long count = kraRepository.count();
//      return APIResponseDTO.builder()
//                                  .timeStamp(System.currentTimeMillis())
//                                  .data(count)
//                                  .message("Total Requested Kra count retrieved successfully")
//                                  .success(true)
//                                  .status(HttpStatus.OK.value())
//                                  .build();
//	}
//}