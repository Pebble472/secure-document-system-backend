package com.securedocs.controller;

import com.securedocs.model.Document;
import com.securedocs.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/documents")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentController {
    
    @Autowired
    private DocumentService documentService;
    
    @PostMapping
    public ResponseEntity<?> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folderId", required = false) String folderId) {
        Document doc = documentService.storeDocument(file, folderId);
        return ResponseEntity.ok(doc);
    }
    
    @GetMapping
    public List<Document> getDocuments(@RequestParam(required = false) String folderId) {
            return documentService.getDocuments(folderId);
        }
    
    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable String id) {
        return ResponseEntity.ok(documentService.getDocument(id));
    }
    
    @GetMapping("/{id}/content")
    public ResponseEntity<Resource> downloadDocument(@PathVariable String id) {
        Resource file = documentService.loadDocumentAsResource(id);
        Document doc = documentService.getDocument(id);
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                       "attachment; filename=\"" + doc.getName() + "\"")
                .body(file);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok().build();
    }
}