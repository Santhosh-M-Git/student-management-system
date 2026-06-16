package com.project.sms;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "doubts")
public class Doubt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doubt_seq")
    @SequenceGenerator(name = "doubt_seq", sequenceName = "doubt_seq", allocationSize = 1)
    private Long id;

    private Long studentId;
    private String subject;
    private String question;
    private String answer;    
    private String status;     
    private LocalDateTime createdAt;
    private LocalDateTime answeredAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = "OPEN";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getAnsweredAt() { return answeredAt; }
    public void setAnsweredAt(LocalDateTime answeredAt) { this.answeredAt = answeredAt; }
}
