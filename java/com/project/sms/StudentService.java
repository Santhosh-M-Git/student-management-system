package com.project.sms;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    public Student save(Student s) {
        s.setEmail(s.getEmail().trim().toLowerCase());
        s.setName(s.getName().trim());
        return repo.save(s);
    }

    public List<Student> getAll() {
        return repo.findAll();
    }

    public Student getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
    }

    public Student update(Long id, Student updated) {
        Student existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
        if (updated.getName() != null && !updated.getName().isBlank())
            existing.setName(updated.getName().trim());
        if (updated.getEmail() != null && !updated.getEmail().isBlank())
            existing.setEmail(updated.getEmail().trim().toLowerCase());
        if (updated.getPhone() != null && !updated.getPhone().isBlank())
            existing.setPhone(updated.getPhone());
        if (updated.getPassword() != null && !updated.getPassword().isBlank())
            existing.setPassword(updated.getPassword());
        return repo.save(existing);
    }

    public void delete(Long id) {
        if (!repo.existsById(id))
            throw new RuntimeException("Student not found with ID: " + id);
        repo.deleteById(id);
    }


    public Student login(String email, String password) {
        String cleanEmail = email.trim().toLowerCase();

        List<Student> students = repo.findAllByEmail(cleanEmail);

        if (students == null || students.isEmpty()) {
            throw new RuntimeException("Email not found");
        }


        for (Student s : students) {
            if (s.getPassword() != null && s.getPassword().trim().equals(password.trim())) {
                return s; // Return first matching student
            }
        }

        throw new RuntimeException("Invalid credentials");
    }
}
