package com.project.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/teachers")
@CrossOrigin("*")
public class TeacherController {

    @Autowired
    private TeacherService service;

  
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Teacher t) {
        try {
            return ResponseEntity.ok(service.login(t.getEmail(), t.getPassword()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

   
    @GetMapping
    public List<Teacher> getAll() {
        return service.getAll();
    }

    
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Teacher t) {
        try {
            return ResponseEntity.ok(service.save(t));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Teacher deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
