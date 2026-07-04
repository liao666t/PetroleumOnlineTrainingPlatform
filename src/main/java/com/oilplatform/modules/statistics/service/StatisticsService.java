package com.oilplatform.modules.statistics.service;

import com.oilplatform.common.result.Result;
import java.util.Map;

public interface StatisticsService {

    Result<Map<String, Object>> getStudentStatistics(Long userId);

    Result<Map<String, Object>> getTeacherStatistics(Long teacherId);

    Result<Map<String, Object>> getAdminStatistics();

    Result<Map<String, Object>> getGlobalDashboard();
}