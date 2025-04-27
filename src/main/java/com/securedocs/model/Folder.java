package com.securedocs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "folders")
public class Folder {
    @Id
    private String id;
    private String name;
    private String description;
    private String parentId;
    private String createdBy;
    private String ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}