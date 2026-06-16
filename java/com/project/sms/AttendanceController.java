package com.project.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@CrossOrigin("*")
public class AttendanceController {

    @Autowired
    private AttendanceService service;

    @PostMapping
    public ResponseEntity<?> mark(@RequestBody Attendance a) {
        try {
            return ResponseEntity.ok(service.markAttendance(a));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{studentId}")
    public List<Attendance> get(@PathVariable Long studentId) {
        return service.getByStudent(studentId);
    }

    @GetMapping("/percentage/{studentId}")
    public double getPercentage(@PathVariable Long studentId) {
        return service.getAttendancePercentage(studentId);
    }

    // CMS: Get ALL attendance records
    @GetMapping("/all")
    public List<Attendance> getAll() {
        return service.getAllAttendance();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.deleteAttendance(id);
            return ResponseEntity.ok("Attendance deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
