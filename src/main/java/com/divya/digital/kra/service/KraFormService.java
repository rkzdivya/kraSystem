package com.divya.digital.kra.service;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.divya.digital.kra.dto.APIResponseDTO;
import com.divya.digital.kra.dto.KraFormDto;
import com.divya.digital.kra.model.KraForm;

public interface KraFormService {

	APIResponseDTO rejectKraForm(Long id);

	APIResponseDTO approveKraForm(Long id);

	APIResponseDTO submitKraForm(Long id);

	APIResponseDTO getKraForm(Long id);

	APIResponseDTO createKraForm(Long userId, KraFormDto kraFormDTO);

	APIResponseDTO updateKraForm(Long id, KraFormDto kraFormDTO);

	APIResponseDTO uploadKraForm(Long userId, MultipartFile file)throws IOException;

	Resource downloadKraForm(Long id);

	APIResponseDTO updateKraForm(Long id, MultipartFile file) throws IOException;

	APIResponseDTO pendingKraForm(Long id);

	APIResponseDTO saveComment(Long userId, Long kraFormId, String managersComment);


}