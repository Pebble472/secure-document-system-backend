package com.securedocs.service;

import com.securedocs.model.Document;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface DocumentService {
    Document storeDocument(MultipartFile file, String folderId);
    List<Document> getDocuments(String folderId);
    Document getDocument(String id);
    Resource loadDocumentAsResource(String id);
    void deleteDocument(String id);
}