package com.project.sms;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudentId(Long studentId);


    Optional<Attendance> findByStudentIdAndAttendanceDate(Long studentId, LocalDate attendanceDate);


    long countByStudentId(Long studentId);
    long countByStudentIdAndStatus(Long studentId, String status);
}
