package com.project.sms;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fee_payments")
public class FeePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fee_seq")
    @SequenceGenerator(name = "fee_seq", sequenceName = "fee_seq", allocationSize = 1)
    private Long id;

    private Long studentId;
    private String feeType;
    
    private Long amount;
    private String status;
    private String term;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = "PENDING";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getFeeType() { return feeType; }
    public void setFeeType(String feeType) { this.feeType = feeType; }
    public Long getAmount() { return amount; }
    public void setAmount(Long amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }
    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
