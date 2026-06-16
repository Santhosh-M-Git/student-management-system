package com.project.sms;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {
    List<FeePayment> findByStudentIdOrderByCreatedAtDesc(Long studentId);
    List<FeePayment> findAllByOrderByCreatedAtDesc();
}
