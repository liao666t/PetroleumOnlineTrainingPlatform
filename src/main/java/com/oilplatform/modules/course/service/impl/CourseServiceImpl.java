package com.oilplatform.modules.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oilplatform.common.exception.BusinessException;
import com.oilplatform.common.result.Result;
import com.oilplatform.modules.course.entity.*;
import com.oilplatform.modules.course.mapper.*;
import com.oilplatform.modules.course.service.CourseService;
import com.oilplatform.modules.study.entity.UserCourse;
import com.oilplatform.modules.study.mapper.StudyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private StudyMapper studyMapper;

    // ==================== 课程 CRUD ====================
    @Override
    public Result<Page<Course>> getCourseList(Integer page, Integer size, Long categoryId, String keyword) {
        Page<Course> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getPublishStatus, 1);
        wrapper.eq(Course::getAuditStatus, 1);
        if (categoryId != null) {
            wrapper.eq(Course::getCategoryId, categoryId);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Course::getCourseName, keyword);
        }
        wrapper.orderByDesc(Course::getCreateTime);
        Page<Course> coursePage = courseMapper.selectPage(pageParam, wrapper);
        return Result.success(coursePage);
    }

    @Override
    public Result<Course> getCourseDetail(Long courseId) {
        Course course = courseMapper.selectById(courseId);
        if (course == null) {
            throw new BusinessException("课程不存在");
        }
        return Result.success(course);
    }

    @Override
    @Transactional
    public Result<?> createCourse(Course course) {
        course.setAuditStatus(0);
        course.setPublishStatus(0);
        course.setScore(new BigDecimal("0"));
        courseMapper.insert(course);
        return Result.success("课程创建成功，请等待审核");
    }

    @Override
    @Transactional
    public Result<?> updateCourse(Course course) {
        Course existCourse = courseMapper.selectById(course.getCourseId());
        if (existCourse == null) {
            throw new BusinessException("课程不存在");
        }
        courseMapper.updateById(course);
        return Result.success("课程更新成功");
    }

    @Override
    @Transactional
    public Result<?> deleteCourse(Long courseId) {
        Course course = courseMapper.selectById(courseId);
        if (course == null) {
            throw new BusinessException("课程不存在");
        }
        courseMapper.deleteById(courseId);
        return Result.success("课程删除成功");
    }

    // ==================== 章节操作（使用 ChapterMapper） ====================
    @Override
    public Result<List<Chapter>> getCourseChapters(Long courseId) {
        List<Chapter> chapters = chapterMapper.selectByCourseId(courseId);
        return Result.success(chapters);
    }

    @Override
    @Transactional
    public Result<?> addChapter(Chapter chapter) {
        chapterMapper.insert(chapter);
        return Result.success("章节添加成功");
    }

    // ==================== 资源操作（使用 ResourceMapper） ====================
    @Override
    public Result<List<Resource>> getChapterResources(Long chapterId) {
        List<Resource> resources = resourceMapper.selectByChapterId(chapterId);
        return Result.success(resources);
    }

    @Override
    @Transactional
    public Result<?> addResource(Resource resource) {
        resourceMapper.insert(resource);
        return Result.success("资源添加成功");
    }

    // ==================== 分类查询（使用 CategoryMapper） ====================
    @Override
    public Result<List<Category>> getCategoryTree() {
        List<Category> parentCategories = categoryMapper.selectByParentId(0L);
        for (Category parent : parentCategories) {
            List<Category> children = categoryMapper.selectByParentId(parent.getCategoryId());
            // 可根据需要组装树形数据，此处暂简化为仅返回一级分类列表
        }
        return Result.success(parentCategories);
    }

    // ==================== 选课与学习进度 ====================
    @Override
    @Transactional
    public Result<?> enrollCourse(Long userId, Long courseId) {
        LambdaQueryWrapper<UserCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCourse::getUserId, userId);
        wrapper.eq(UserCourse::getCourseId, courseId);
        UserCourse exist = studyMapper.selectOne(wrapper);
        if (exist != null) {
            throw new BusinessException("已选该课程，无需重复选课");
        }
        UserCourse userCourse = new UserCourse();
        userCourse.setUserId(userId);
        userCourse.setCourseId(courseId);
        userCourse.setStudyProgress(new BigDecimal("0"));
        userCourse.setSelectTime(LocalDateTime.now());
        studyMapper.insert(userCourse);
        return Result.success("选课成功");
    }

    @Override
    @Transactional
    public Result<?> updateStudyProgress(Long userId, Long courseId, Double progress) {
        LambdaQueryWrapper<UserCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCourse::getUserId, userId);
        wrapper.eq(UserCourse::getCourseId, courseId);
        UserCourse userCourse = studyMapper.selectOne(wrapper);
        if (userCourse == null) {
            throw new BusinessException("未选该课程");
        }
        userCourse.setStudyProgress(new BigDecimal(progress));
        userCourse.setLastStudyTime(LocalDateTime.now());
        studyMapper.updateById(userCourse);
        return Result.success("学习进度更新成功");
    }
}