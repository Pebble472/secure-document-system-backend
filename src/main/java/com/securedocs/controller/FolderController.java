package com.securedocs.controller;

import com.securedocs.model.Folder;
import com.securedocs.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folders")
@CrossOrigin(origins = "http://localhost:4200")
public class FolderController {
    
    @Autowired
    private FolderService folderService;
    
    @PostMapping
    public ResponseEntity<Folder> createFolder(@RequestBody Folder folder) {
        return ResponseEntity.ok(folderService.createFolder(folder));
    }
    
    @GetMapping
    public ResponseEntity<List<Folder>> getFolders(
            @RequestParam(required = false) String parentId) {
        return ResponseEntity.ok(folderService.getFolders(parentId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Folder> getFolder(@PathVariable String id) {
        return ResponseEntity.ok(folderService.getFolder(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Folder> updateFolder(
            @PathVariable String id, 
            @RequestBody Folder folder) {
        return ResponseEntity.ok(folderService.updateFolder(id, folder));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFolder(@PathVariable String id) {
        folderService.deleteFolder(id);
        return ResponseEntity.ok().build();
    }
}