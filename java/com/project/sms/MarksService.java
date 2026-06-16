package com.project.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MarksService {

    @Autowired
    private MarksRepository repo;

    public Marks addMarks(Marks m) {
        return repo.save(m);
    }

    public List<Marks> getMarks(Long studentId) {
        return repo.findByStudentId(studentId);
    }


    public List<Marks> getAllMarks() {
        return repo.findAll();
    }


    public void deleteMarks(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Marks entry not found with ID: " + id);
        }
        repo.deleteById(id);
    }


    public double getAverage(Long studentId) {
        List<Marks> list = repo.findByStudentId(studentId);
        if (list.isEmpty()) return 0;

        Map<String, Double> subjectAverages = list.stream()
                .collect(Collectors.groupingBy(
                        Marks::getSubject,
                        Collectors.averagingInt(Marks::getMarks)
                ));

        return subjectAverages.values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }
}
