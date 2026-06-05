//package com.divya.digital.kra.service.impl;
//
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.divya.digital.kra.exception.FileStorageException;
//import com.divya.digital.kra.service.StorageService;
//
//@Service
//public class StorageServiceImpl implements StorageService {
//
//    @Value("${upload.path}")
//    private String uploadPath;
//
//    private final Path root = Paths.get("uploads");
//
//    @Override
//    public void save(MultipartFile file) {
//        try {
//            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
//        } catch (Exception e) {
//            throw new FileStorageException("Could not store the file. Error: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public Resource load(String filename) {
//        try {
//            Path file = root.resolve(filename);
//            Resource resource = new FileSystemResource(file.toFile());
//            if (resource.exists() || resource.isReadable()) {
//                return resource;
//            } else {
//                throw new FileStorageException("Could not read the file!");
//            }
//        } catch (Exception e) {
//            throw new FileStorageException("Error: " + e.getMessage());
//        }
//    }
//    
//}    
