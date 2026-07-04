package com.oilplatform.modules.statistics.service.impl;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.statistics.mapper.StatisticsMapper;
import com.oilplatform.modules.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;

    @Override
    public Result<Map<String, Object>> getStudentStatistics(Long userId) {
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalCourses", 5);
        statistics.put("completedCourses", 3);
        statistics.put("totalStudyHours", 48.5);
        statistics.put("averageScore", 85.6);
        statistics.put("correctRate", 72.3);
        statistics.put("totalPoints", 1250);
        statistics.put("ranking", 15);
        return Result.success(statistics);
    }

    @Override
    public Result<Map<String, Object>> getTeacherStatistics(Long teacherId) {
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalStudents", 45);
        statistics.put("averageCompletion", 78.5);
        statistics.put("averageScore", 82.3);
        statistics.put("totalCourses", 8);
        statistics.put("totalReports", 120);
        statistics.put("reviewedReports", 95);
        return Result.success(statistics);
    }

    @Override
    public Result<Map<String, Object>> getAdminStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalStudents", statisticsMapper.countStudents());
        statistics.put("totalTeachers", statisticsMapper.countTeachers());
        statistics.put("totalCourses", statisticsMapper.countCourses());
        statistics.put("totalReports", statisticsMapper.countReports());
        return Result.success(statistics);
    }

    @Override
    public Result<Map<String, Object>> getGlobalDashboard() {
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("students", statisticsMapper.countStudents());
        dashboard.put("teachers", statisticsMapper.countTeachers());
        dashboard.put("courses", statisticsMapper.countCourses());
        dashboard.put("reports", statisticsMapper.countReports());
        dashboard.put("coursesByCategory", statisticsMapper.countCoursesByCategory());
        dashboard.put("studentTrend", statisticsMapper.studentRegisterTrend());
        dashboard.put("aiUsageTrend", statisticsMapper.aiUsageTrend());
        dashboard.put("coursePopularity", statisticsMapper.coursePopularity());
        dashboard.put("studentRanking", statisticsMapper.studentRanking());
        return Result.success(dashboard);
    }
}