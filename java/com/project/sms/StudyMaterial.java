package com.project.sms;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "study_materials")
public class StudyMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_seq")
    @SequenceGenerator(name = "material_seq", sequenceName = "material_seq", allocationSize = 1)
    private Long id;

    private String title;
    private String subject;
    private String description;
    private String fileUrl;     
    private String fileType;   
    private String uploadedBy;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() { this.createdAt = LocalDateTime.now(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    public String getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(String uploadedBy) { this.uploadedBy = uploadedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
