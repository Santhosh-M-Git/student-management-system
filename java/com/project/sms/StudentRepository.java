package com.project.sms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

 
    @Query("SELECT s FROM Student s WHERE LOWER(TRIM(s.email)) = LOWER(TRIM(:email)) ORDER BY s.id ASC")
    List<Student> findAllByEmail(@Param("email") String email);

    @Query("SELECT s FROM Student s WHERE LOWER(TRIM(s.email)) = LOWER(TRIM(:email)) ORDER BY s.id ASC")
    Student findByEmail(@Param("email") String email);
}
