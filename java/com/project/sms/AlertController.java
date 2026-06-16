package com.project.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
@CrossOrigin("*")
public class AlertController {

    @Autowired
    private AlertRepository repo;


    @GetMapping
    public List<Alert> getAll() {
        return repo.findAll();
    }


    @GetMapping("/{studentId}")
    public List<Alert> getByStudent(@PathVariable Long studentId) {
        return repo.findByStudentId(studentId);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alert not found");
        }
        repo.deleteById(id);
        return ResponseEntity.ok("Alert dismissed");
    }
}
