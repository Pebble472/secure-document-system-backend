package com.securedocs.service.impl;

import com.securedocs.model.Document;
import com.securedocs.service.DocumentService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {
    private final Path rootLocation = Paths.get("uploads");

    public DocumentServiceImpl() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    @Override
    public Document storeDocument(MultipartFile file, String folderId) {
        try {
            String uuid = UUID.randomUUID().toString();
            Path destinationFile = rootLocation.resolve(uuid);
            Files.copy(file.getInputStream(), destinationFile);
            
            Document doc = new Document();
            doc.setId(uuid);
            doc.setName(file.getOriginalFilename());
            doc.setContentType(file.getContentType());
            doc.setSize(file.getSize());
            doc.setStoragePath(destinationFile.toString());
            doc.setFolderId(folderId);
            doc.setChecksum("placeholder"); // Implement actual checksum calculation
            doc.setCreatedBy("currentUser"); // Get from authentication
            doc.setOwnerId("currentUser"); // Get from authentication
            doc.setCreatedAt(LocalDateTime.now());
            doc.setUpdatedAt(LocalDateTime.now());
            
            // In a real implementation, save to repository
            
            return doc;
        } catch (Exception e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public List<Document> getDocuments(String folderId) {
        // Implement repository call
        return new ArrayList<>();
    }

    @Override
    public Document getDocument(String id) {
        // Implement repository call
        Document doc = new Document();
        doc.setId(id);
        return doc;
    }

    @Override
    public Resource loadDocumentAsResource(String id) {
        try {
            Document doc = getDocument(id);
            Path file = Paths.get(doc.getStoragePath());
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read file: " + id, e);
        }
    }

    @Override
    public void deleteDocument(String id) {
        // Implement repository call and file deletion
    }
}