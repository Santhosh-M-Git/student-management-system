package com.project.sms;

import jakarta.persistence.*;

@Entity
public class Marks {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "marks_gen")
    @SequenceGenerator(name = "marks_gen", sequenceName = "marks_seq", allocationSize = 1)
    private Long id;

    private Long studentId;
    private String subject;
    private int marks;
    
    
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getMarks() {
		return marks;
	}
	public void setMarks(int marks) {
		this.marks = marks;
	}

    
    
}