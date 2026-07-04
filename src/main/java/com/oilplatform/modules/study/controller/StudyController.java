package com.oilplatform.modules.study.controller;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.study.service.StudyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/study")
public class StudyController {

    @Autowired
    private StudyService studyService;

    /**
     * 获取我的选课列表
     */
    @GetMapping("/courses")
    public Result<?> getMyEnrolledCourses(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Long userId = (Long) request.getAttribute("userId");
        return studyService.getMyEnrolledCourses(userId, page, size);
    }

    /**
     * 获取最近学习记录
     */
    @GetMapping("/recent")
    public Result<?> getRecentStudyRecords(
            HttpServletRequest request,
            @RequestParam(defaultValue = "5") Integer limit) {
        Long userId = (Long) request.getAttribute("userId");
        return studyService.getRecentStudyRecords(userId, limit);
    }

    /**
     * 获取个人学习统计概览
     */
    @GetMapping("/overview")
    public Result<?> getStudyOverview(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return studyService.getStudyOverview(userId);
    }

    /**
     * 收藏/取消收藏课程
     */
    @PostMapping("/favorite/{courseId}")
    public Result<?> toggleFavorite(
            @PathVariable Long courseId,
            @RequestParam(defaultValue = "true") boolean isFavorite,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return studyService.toggleFavorite(userId, courseId, isFavorite);
    }

    /**
     * 获取收藏课程列表
     */
    @GetMapping("/favorites")
    public Result<?> getFavoriteCourses(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Long userId = (Long) request.getAttribute("userId");
        return studyService.getFavoriteCourses(userId, page, size);
    }
}