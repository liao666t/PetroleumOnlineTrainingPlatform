package com.oilplatform.modules.course.service;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.course.entity.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface CourseService {

    Result<Page<Course>> getCourseList(Integer page, Integer size, Long categoryId, String keyword);

    Result<Course> getCourseDetail(Long courseId);

    Result<List<Chapter>> getCourseChapters(Long courseId);

    Result<List<Resource>> getChapterResources(Long chapterId);

    Result<?> createCourse(Course course);

    Result<?> updateCourse(Course course);

    Result<?> deleteCourse(Long courseId);

    Result<?> addChapter(Chapter chapter);

    Result<?> addResource(Resource resource);

    Result<List<Category>> getCategoryTree();

    Result<?> enrollCourse(Long userId, Long courseId);

    Result<?> updateStudyProgress(Long userId, Long courseId, Double progress);
}