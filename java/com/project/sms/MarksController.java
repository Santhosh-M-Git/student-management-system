package com.project.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marks")
@CrossOrigin("*")
public class MarksController {

    @Autowired
    private MarksService service;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Marks m) {
        try {
            return ResponseEntity.ok(service.addMarks(m));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{studentId}")
    public List<Marks> get(@PathVariable Long studentId) {
        return service.getMarks(studentId);
    }

    @GetMapping("/average/{studentId}")
    public double avg(@PathVariable Long studentId) {
        return service.getAverage(studentId);
    }


    @GetMapping("/all")
    public List<Marks> getAll() {
        return service.getAllMarks();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.deleteMarks(id);
            return ResponseEntity.ok("Marks deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
