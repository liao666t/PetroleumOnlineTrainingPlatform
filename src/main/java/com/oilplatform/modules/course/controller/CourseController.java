package com.oilplatform.modules.course.controller;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.course.entity.*;
import com.oilplatform.modules.course.service.CourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/list")
    public Result<Page<Course>> getCourseList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword) {
        return courseService.getCourseList(page, size, categoryId, keyword);
    }

    @GetMapping("/detail/{courseId}")
    public Result<Course> getCourseDetail(@PathVariable Long courseId) {
        return courseService.getCourseDetail(courseId);
    }

    @GetMapping("/{courseId}/chapters")
    public Result<List<Chapter>> getCourseChapters(@PathVariable Long courseId) {
        return courseService.getCourseChapters(courseId);
    }

    @GetMapping("/chapter/{chapterId}/resources")
    public Result<List<Resource>> getChapterResources(@PathVariable Long chapterId) {
        return courseService.getChapterResources(chapterId);
    }

    @PostMapping("/create")
    public Result<?> createCourse(@RequestBody Course course, HttpServletRequest request) {
        Long teacherId = (Long) request.getAttribute("userId");
        course.setTeacherId(teacherId);
        return courseService.createCourse(course);
    }

    @PutMapping("/update")
    public Result<?> updateCourse(@RequestBody Course course) {
        return courseService.updateCourse(course);
    }

    @DeleteMapping("/delete/{courseId}")
    public Result<?> deleteCourse(@PathVariable Long courseId) {
        return courseService.deleteCourse(courseId);
    }

    @PostMapping("/chapter/add")
    public Result<?> addChapter(@RequestBody Chapter chapter) {
        return courseService.addChapter(chapter);
    }

    @PostMapping("/resource/add")
    public Result<?> addResource(@RequestBody Resource resource) {
        return courseService.addResource(resource);
    }

    @GetMapping("/categories")
    public Result<List<Category>> getCategoryTree() {
        return courseService.getCategoryTree();
    }

    @PostMapping("/enroll/{courseId}")
    public Result<?> enrollCourse(@PathVariable Long courseId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return courseService.enrollCourse(userId, courseId);
    }

    @PutMapping("/progress")
    public Result<?> updateStudyProgress(@RequestBody Map<String, Object> params,
                                         HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long courseId = Long.valueOf(params.get("courseId").toString());
        Double progress = Double.valueOf(params.get("progress").toString());
        return courseService.updateStudyProgress(userId, courseId, progress);
    }
}