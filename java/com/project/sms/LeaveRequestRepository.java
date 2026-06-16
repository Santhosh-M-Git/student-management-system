package com.project.sms;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByStudentIdOrderByCreatedAtDesc(Long studentId);
    List<LeaveRequest> findAllByOrderByCreatedAtDesc();
    List<LeaveRequest> findByStatusOrderByCreatedAtDesc(String status);
}
