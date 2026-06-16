package com.project.sms;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alert_gen")
    @SequenceGenerator(name = "alert_gen", sequenceName = "alert_seq", allocationSize = 1)
    private Long id;

    private Long studentId;
    private String message;
    private LocalDateTime createdAt = LocalDateTime.now();
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	@PrePersist
	public void onCreate() {
	    this.createdAt = LocalDateTime.now();
	}
    
    
}