package com.project.sms;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface DoubtRepository extends JpaRepository<Doubt, Long> {
    List<Doubt> findByStudentIdOrderByCreatedAtDesc(Long studentId);
    List<Doubt> findAllByOrderByCreatedAtDesc();
    List<Doubt> findByStatusOrderByCreatedAtDesc(String status);
}
