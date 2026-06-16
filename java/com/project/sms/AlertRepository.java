package com.project.sms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {
	
	List<Alert> findByStudentIdAndMessage(Long studentId, String message);
	
	List<Alert> findByStudentId(Long studentId);
	
	boolean existsByStudentIdAndMessage(Long studentId, String string);

}
