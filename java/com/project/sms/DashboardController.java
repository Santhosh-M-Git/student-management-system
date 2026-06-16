package com.project.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin("*")
public class DashboardController {

    @Autowired
    private DashboardService service;

    @GetMapping("/{studentId}")
    public DashboardDTO get(@PathVariable Long studentId) {
        return service.getDashboard(studentId);
    }
}