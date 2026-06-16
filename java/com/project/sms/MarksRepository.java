package com.project.sms;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MarksRepository extends JpaRepository<Marks, Long> {

    List<Marks> findByStudentId(Long studentId);

}