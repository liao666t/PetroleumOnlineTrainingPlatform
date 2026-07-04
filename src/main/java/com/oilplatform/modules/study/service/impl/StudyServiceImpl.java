package com.oilplatform.modules.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oilplatform.common.result.Result;
import com.oilplatform.modules.course.entity.Course;
import com.oilplatform.modules.course.mapper.CourseMapper;
import com.oilplatform.modules.study.entity.UserCourse;
import com.oilplatform.modules.study.mapper.StudyMapper;
import com.oilplatform.modules.study.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudyServiceImpl implements StudyService {

    @Autowired
    private StudyMapper studyMapper;

    @Autowired
    private CourseMapper courseMapper;

    // ==================== 原有空实现保留（若无具体逻辑可不加内容） ====================
    // 若有其他方法已在接口中定义但未实现，可继续留空
    // 这里假设接口中原有的方法已通过其他方式实现或不再需要

    // ==================== 扩展方法 ====================

    /**
     * 获取我的选课列表（分页，包含学习进度）
     */
    @Override
    public Result<?> getMyEnrolledCourses(Long userId, Integer page, Integer size) {
        Page<UserCourse> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<UserCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCourse::getUserId, userId);
        wrapper.orderByDesc(UserCourse::getSelectTime);
        Page<UserCourse> userCoursePage = studyMapper.selectPage(pageParam, wrapper);

        List<Map<String, Object>> records = userCoursePage.getRecords().stream().map(uc -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", uc.getId());
            map.put("courseId", uc.getCourseId());
            map.put("studyProgress", uc.getStudyProgress());
            map.put("selectTime", uc.getSelectTime());
            map.put("lastStudyTime", uc.getLastStudyTime());
            map.put("isFavorite", uc.getIsFavorite());   // 返回收藏状态
            // 关联课程基本信息
            Course course = courseMapper.selectById(uc.getCourseId());
            if (course != null) {
                map.put("courseName", course.getCourseName());
                map.put("cover", course.getCover());
                map.put("score", course.getScore());
            }
            return map;
        }).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("records", records);
        result.put("total", userCoursePage.getTotal());
        result.put("current", userCoursePage.getCurrent());
        result.put("size", userCoursePage.getSize());
        return Result.success(result);
    }

    /**
     * 获取最近学习记录
     */
    @Override
    public Result<?> getRecentStudyRecords(Long userId, Integer limit) {
        LambdaQueryWrapper<UserCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCourse::getUserId, userId);
        wrapper.isNotNull(UserCourse::getLastStudyTime);
        wrapper.orderByDesc(UserCourse::getLastStudyTime);
        wrapper.last("LIMIT " + (limit != null ? limit : 5));
        List<UserCourse> list = studyMapper.selectList(wrapper);

        List<Map<String, Object>> records = list.stream().map(uc -> {
            Map<String, Object> map = new HashMap<>();
            map.put("courseId", uc.getCourseId());
            map.put("studyProgress", uc.getStudyProgress());
            map.put("lastStudyTime", uc.getLastStudyTime());
            Course course = courseMapper.selectById(uc.getCourseId());
            if (course != null) {
                map.put("courseName", course.getCourseName());
                map.put("cover", course.getCover());
            }
            return map;
        }).collect(Collectors.toList());

        return Result.success(records);
    }

    /**
     * 获取个人学习统计概览
     */
    @Override
    public Result<Map<String, Object>> getStudyOverview(Long userId) {
        LambdaQueryWrapper<UserCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCourse::getUserId, userId);
        List<UserCourse> list = studyMapper.selectList(wrapper);

        Map<String, Object> overview = new HashMap<>();
        overview.put("totalEnrolled", list.size());
        long completed = list.stream()
                .filter(uc -> uc.getStudyProgress() != null && uc.getStudyProgress().doubleValue() >= 100.0)
                .count();
        overview.put("completedCourses", completed);
        double avgProgress = list.stream()
                .filter(uc -> uc.getStudyProgress() != null)
                .mapToDouble(uc -> uc.getStudyProgress().doubleValue())
                .average().orElse(0.0);
        overview.put("averageProgress", Math.round(avgProgress * 100.0) / 100.0);
        Optional<LocalDateTime> latest = list.stream()
                .filter(uc -> uc.getLastStudyTime() != null)
                .map(UserCourse::getLastStudyTime)
                .max(LocalDateTime::compareTo);
        overview.put("latestStudyTime", latest.orElse(null));
        return Result.success(overview);
    }

    /**
     * 收藏或取消收藏课程
     */
    @Override
    public Result<?> toggleFavorite(Long userId, Long courseId, boolean isFavorite) {
        LambdaQueryWrapper<UserCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCourse::getUserId, userId);
        wrapper.eq(UserCourse::getCourseId, courseId);
        UserCourse userCourse = studyMapper.selectOne(wrapper);

        if (userCourse == null) {
            // 未选课时自动选课并设置收藏状态
            userCourse = new UserCourse();
            userCourse.setUserId(userId);
            userCourse.setCourseId(courseId);
            userCourse.setStudyProgress(new BigDecimal("0"));
            userCourse.setSelectTime(LocalDateTime.now());
            userCourse.setIsFavorite(isFavorite ? 1 : 0);
            studyMapper.insert(userCourse);
        } else {
            userCourse.setIsFavorite(isFavorite ? 1 : 0);
            studyMapper.updateById(userCourse);
        }
        return Result.success(isFavorite ? "收藏成功" : "已取消收藏");
    }

    /**
     * 获取我的收藏课程列表
     */
    @Override
    public Result<?> getFavoriteCourses(Long userId, Integer page, Integer size) {
        Page<UserCourse> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<UserCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCourse::getUserId, userId);
        wrapper.eq(UserCourse::getIsFavorite, 1);
        wrapper.orderByDesc(UserCourse::getSelectTime);
        Page<UserCourse> pageResult = studyMapper.selectPage(pageParam, wrapper);

        List<Map<String, Object>> records = pageResult.getRecords().stream().map(uc -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", uc.getId());
            map.put("courseId", uc.getCourseId());
            map.put("studyProgress", uc.getStudyProgress());
            map.put("selectTime", uc.getSelectTime());
            map.put("lastStudyTime", uc.getLastStudyTime());
            Course course = courseMapper.selectById(uc.getCourseId());
            if (course != null) {
                map.put("courseName", course.getCourseName());
                map.put("cover", course.getCover());
                map.put("score", course.getScore());
            }
            return map;
        }).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("records", records);
        result.put("total", pageResult.getTotal());
        result.put("current", pageResult.getCurrent());
        result.put("size", pageResult.getSize());
        return Result.success(result);
    }
}