package com.project.sms;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, Long> {
    List<StudyMaterial> findAllByOrderByCreatedAtDesc();
    List<StudyMaterial> findBySubjectOrderByCreatedAtDesc(String subject);
}
