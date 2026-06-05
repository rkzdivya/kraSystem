package com.divya.digital.kra.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

public interface StorageService {
	void save(MultipartFile file);

	Resource load(String filename);
}
