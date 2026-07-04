package com.oilplatform.modules.ai.controller;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.ai.service.AIService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    @PostMapping("/chat")
    public Result<Map<String, Object>> chat(@RequestBody Map<String, String> chatData,
                                            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String question = chatData.get("question");
        return aiService.chat(userId, question);
    }

    @GetMapping("/history")
    public Result<?> getChatHistory(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return aiService.getChatHistory(userId);
    }

    @GetMapping("/weak-points")
    public Result<?> analyzeWeakPoints(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return aiService.analyzeWeakPoints(userId);
    }

    @GetMapping("/study-plan")
    public Result<?> generateStudyPlan(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return aiService.generateStudyPlan(userId);
    }

    @PostMapping("/report-framework")
    public Result<?> generateReportFramework(@RequestBody Map<String, String> data) {
        String topic = data.get("topic");
        return aiService.generateReportFramework(topic);
    }

    @DeleteMapping("/history")
    public Result<?> clearHistory(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return aiService.clearHistory(userId);
    }
}