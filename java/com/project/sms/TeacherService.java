package com.project.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository repo;

    public Teacher login(String email, String password) {
        String cleanEmail = email.trim().toLowerCase();
        Teacher teacher = repo.findByEmail(cleanEmail);
        if (teacher == null) {
            throw new RuntimeException("Teacher not found");
        }
        if (teacher.getPassword().trim().equals(password.trim())) {
            return teacher;
        }
        throw new RuntimeException("Invalid credentials");
    }

    public List<Teacher> getAll() {
        return repo.findAll();
    }

    public Teacher save(Teacher t) {
        t.setEmail(t.getEmail().trim().toLowerCase());
        return repo.save(t);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Teacher not found with ID: " + id);
        }
        repo.deleteById(id);
    }
}
