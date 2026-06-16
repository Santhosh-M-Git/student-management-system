package com.project.sms;

import jakarta.persistence.*;

import java.time.LocalDate;
//import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "att_gen")
    @SequenceGenerator(name = "att_gen", sequenceName = "attendance_seq", allocationSize = 1)
    private Long id;

    private Long studentId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate attendanceDate;

    private String status;
    
    

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

	public LocalDate getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(LocalDate attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    
 
}