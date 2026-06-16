package com.project.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository repo;

    @Autowired
    private AlertRepository alertRepo;


    public Attendance markAttendance(Attendance a) {
        List<Attendance> existing = repo.findByStudentId(a.getStudentId());
        boolean alreadyMarked = existing.stream()
                .anyMatch(att -> att.getAttendanceDate().equals(a.getAttendanceDate()));

        if (alreadyMarked) {
            throw new RuntimeException("Attendance already marked for Student ID "
                    + a.getStudentId() + " on " + a.getAttendanceDate());
        }
        return repo.save(a);
    }

    public List<Attendance> getByStudent(Long studentId) {
        return repo.findByStudentId(studentId);
    }


    public List<Attendance> getAllAttendance() {
        return repo.findAll();
    }


    public void deleteAttendance(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Attendance record not found with ID: " + id);
        }
        repo.deleteById(id);
    }

    public double getAttendancePercentage(Long studentId) {
        List<Attendance> list = repo.findByStudentId(studentId);
        if (list.isEmpty()) return 0;

        long presentCount = list.stream()
                .filter(a -> "Present".equalsIgnoreCase(a.getStatus()))
                .count();

        double percentage = (presentCount * 100.0) / list.size();

        if (percentage < 75.0) {
            List<Alert> existingAlerts = alertRepo.findByStudentIdAndMessage(studentId, "Attendance below 75%");
            if (existingAlerts.isEmpty()) {
                Alert alert = new Alert();
                alert.setStudentId(studentId);
                alert.setMessage("Attendance below 75%");
                alertRepo.save(alert);
            }
        } else {
            List<Alert> oldAlerts = alertRepo.findByStudentIdAndMessage(studentId, "Attendance below 75%");
            if (!oldAlerts.isEmpty()) {
                alertRepo.deleteAll(oldAlerts);
            }
        }

        return percentage;
    }
}
