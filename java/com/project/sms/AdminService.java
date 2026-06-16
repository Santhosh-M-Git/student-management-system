package com.project.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository repo;

    public Admin login(String email, String password) {
        Admin admin = repo.findByEmail(email);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        } else {
            throw new RuntimeException("Invalid Teacher/Admin credentials");
        }
    }
}