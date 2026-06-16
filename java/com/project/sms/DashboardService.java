package com.project.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.ArrayList;

@Service
public class DashboardService {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private MarksRepository marksRepo;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private AlertRepository alertRepo;

    public DashboardDTO getDashboard(Long studentId) {
        DashboardDTO dto = new DashboardDTO();

    
        Student s = studentRepo.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        dto.setName(s.getName());

     
        double percent = attendanceService.getAttendancePercentage(studentId);
        dto.setAttendancePercentage(percent);

      
        List<Marks> rawMarksList = marksRepo.findByStudentId(studentId);

        Map<String, Double> groupedMarks = rawMarksList.stream()
            .collect(Collectors.groupingBy(
                Marks::getSubject,
                Collectors.averagingInt(Marks::getMarks)
            ));

        List<Marks> cleanList = new ArrayList<>();
        groupedMarks.forEach((subject, score) -> {
            Marks m = new Marks();
            m.setSubject(subject);
            m.setMarks(score.intValue());
            cleanList.add(m);
        });
        dto.setMarksList(cleanList);

    
        double avg = cleanList.stream()
                .mapToInt(Marks::getMarks)
                .average()
                .orElse(0.0);
        dto.setAverageMarks(avg);

        if (avg >= 40 && percent >= 75) {
            dto.setPredictedResult("Likely to Pass (Good Standing)");
        } else if (avg < 40 && percent < 75) {
            dto.setPredictedResult("High Risk of Failure (Low Marks & Attendance)");
        } else {
            dto.setPredictedResult("At Risk (Improvement Needed)");
        }

  
        Marks weakSubject = cleanList.stream()
                .min(Comparator.comparingInt(Marks::getMarks))
                .orElse(null);

        if (weakSubject != null && weakSubject.getMarks() < 75) {
            dto.setSubjectSuggestion(
                "Weak Area Identified: " + weakSubject.getSubject()
                + " (" + weakSubject.getMarks() + "/100)"
                + " - Extra focus and practice needed in this subject!"
            );
        } else if (weakSubject != null) {
            dto.setSubjectSuggestion(
                "All subjects are above 75! Lowest score in: "
                + weakSubject.getSubject()
                + " (" + weakSubject.getMarks() + "/100)"
                + " - Excellent! Keep it up!"
            );
        } else {
            dto.setSubjectSuggestion("No marks data available yet.");
        }

     
        List<Alert> alerts = alertRepo.findByStudentId(studentId);
        dto.setAlerts(alerts);

        return dto;
    }
}
