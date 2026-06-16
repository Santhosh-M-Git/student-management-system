package com.project.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin("*")
public class InteractiveController {

    @Autowired private LeaveRequestRepository leaveRepo;
    @Autowired private FeePaymentRepository feeRepo;
    @Autowired private AnnouncementRepository announcementRepo;
    @Autowired private DoubtRepository doubtRepo;
    @Autowired private StudyMaterialRepository materialRepo;
    @Autowired private StudentRepository studentRepo;



    @PostMapping("/leave")
    public ResponseEntity<?> applyLeave(@RequestBody LeaveRequest req) {
        try {
            return ResponseEntity.ok(leaveRepo.save(req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/leave/student/{studentId}")
    public List<LeaveRequest> getStudentLeaves(@PathVariable Long studentId) {
        return leaveRepo.findByStudentIdOrderByCreatedAtDesc(studentId);
    }

    @GetMapping("/leave/all")
    public List<LeaveRequest> getAllLeaves() {
        return leaveRepo.findAllByOrderByCreatedAtDesc();
    }

    @PutMapping("/leave/{id}/status")
    public ResponseEntity<?> updateLeaveStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(required = false) String note) {
        return leaveRepo.findById(id).map(req -> {
            req.setStatus(status.toUpperCase());
            if (note != null) req.setTeacherNote(note);
            return ResponseEntity.ok(leaveRepo.save(req));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/leave/{id}")
    public ResponseEntity<?> deleteLeave(@PathVariable Long id) {
        if (!leaveRepo.existsById(id)) return ResponseEntity.notFound().build();
        leaveRepo.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

  

    @PostMapping("/fees")
    public ResponseEntity<?> addFee(@RequestBody FeePayment fee) {
        try {
            return ResponseEntity.ok(feeRepo.save(fee));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/fees/student/{studentId}")
    public List<FeePayment> getStudentFees(@PathVariable Long studentId) {
        return feeRepo.findByStudentIdOrderByCreatedAtDesc(studentId);
    }

    @GetMapping("/fees/all")
    public List<FeePayment> getAllFees() {
        return feeRepo.findAllByOrderByCreatedAtDesc();
    }

    @PutMapping("/fees/{id}/status")
    public ResponseEntity<?> updateFeeStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return feeRepo.findById(id).map(fee -> {
            fee.setStatus(status.toUpperCase());
            if ("PAID".equals(status.toUpperCase())) {
                fee.setPaidAt(LocalDateTime.now());
            }
            return ResponseEntity.ok(feeRepo.save(fee));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/fees/{id}")
    public ResponseEntity<?> deleteFee(@PathVariable Long id) {
        if (!feeRepo.existsById(id)) return ResponseEntity.notFound().build();
        feeRepo.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }



    @PostMapping("/announcements")
    public ResponseEntity<?> postAnnouncement(@RequestBody Announcement a) {
        try {
            return ResponseEntity.ok(announcementRepo.save(a));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/announcements")
    public List<Announcement> getAllAnnouncements() {
        return announcementRepo.findAllByOrderByCreatedAtDesc();
    }

    @DeleteMapping("/announcements/{id}")
    public ResponseEntity<?> deleteAnnouncement(@PathVariable Long id) {
        if (!announcementRepo.existsById(id)) return ResponseEntity.notFound().build();
        announcementRepo.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }



    @PostMapping("/doubts")
    public ResponseEntity<?> askDoubt(@RequestBody Doubt doubt) {
        try {
            return ResponseEntity.ok(doubtRepo.save(doubt));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/doubts/student/{studentId}")
    public List<Doubt> getStudentDoubts(@PathVariable Long studentId) {
        return doubtRepo.findByStudentIdOrderByCreatedAtDesc(studentId);
    }

    @GetMapping("/doubts/all")
    public List<Doubt> getAllDoubts() {
        return doubtRepo.findAllByOrderByCreatedAtDesc();
    }

    @PutMapping("/doubts/{id}/answer")
    public ResponseEntity<?> answerDoubt(
            @PathVariable Long id,
            @RequestParam String answer) {
        return doubtRepo.findById(id).map(d -> {
            d.setAnswer(answer);
            d.setStatus("ANSWERED");
            d.setAnsweredAt(LocalDateTime.now());
            return ResponseEntity.ok(doubtRepo.save(d));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/doubts/{id}")
    public ResponseEntity<?> deleteDoubt(@PathVariable Long id) {
        if (!doubtRepo.existsById(id)) return ResponseEntity.notFound().build();
        doubtRepo.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }



    @PostMapping("/materials")
    public ResponseEntity<?> uploadMaterial(@RequestBody StudyMaterial m) {
        try {
            return ResponseEntity.ok(materialRepo.save(m));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/materials")
    public List<StudyMaterial> getAllMaterials() {
        return materialRepo.findAllByOrderByCreatedAtDesc();
    }

    @DeleteMapping("/materials/{id}")
    public ResponseEntity<?> deleteMaterial(@PathVariable Long id) {
        if (!materialRepo.existsById(id)) return ResponseEntity.notFound().build();
        materialRepo.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}
