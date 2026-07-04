package com.oilplatform.modules.statistics.controller;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.statistics.service.StatisticsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/student")
    public Result<Map<String, Object>> getStudentStatistics(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return statisticsService.getStudentStatistics(userId);
    }

    @GetMapping("/teacher")
    public Result<Map<String, Object>> getTeacherStatistics(HttpServletRequest request) {
        Long teacherId = (Long) request.getAttribute("userId");
        return statisticsService.getTeacherStatistics(teacherId);
    }

    @GetMapping("/admin")
    public Result<Map<String, Object>> getAdminStatistics() {
        return statisticsService.getAdminStatistics();
    }

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> getGlobalDashboard() {
        return statisticsService.getGlobalDashboard();
    }
}