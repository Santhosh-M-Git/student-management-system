package com.project.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AdminService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Admin admin) {
        try {
            return ResponseEntity.ok(service.login(admin.getEmail(), admin.getPassword()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
