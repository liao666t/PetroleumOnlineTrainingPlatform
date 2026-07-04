package com.oilplatform.modules.ai.service;

import com.oilplatform.common.result.Result;
import java.util.Map;

public interface AIService {

    Result<Map<String, Object>> chat(Long userId, String question);

    Result<?> getChatHistory(Long userId);

    Result<?> analyzeWeakPoints(Long userId);

    Result<?> generateStudyPlan(Long userId);

    Result<?> generateReportFramework(String topic);

    Result<?> clearHistory(Long userId);
}