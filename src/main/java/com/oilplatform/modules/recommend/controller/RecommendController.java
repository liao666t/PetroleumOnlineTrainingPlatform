package com.oilplatform.modules.recommend.controller;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.recommend.service.RecommendService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    @GetMapping("/courses")
    public Result<List<Long>> getRecommendedCourses(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return recommendService.getRecommendedCourses(userId);
    }

    @GetMapping("/similar/{courseId}")
    public Result<List<Long>> getSimilarCourses(@PathVariable Long courseId) {
        return recommendService.getSimilarCourses(courseId);
    }
}