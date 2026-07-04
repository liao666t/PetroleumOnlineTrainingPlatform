package com.oilplatform.modules.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oilplatform.common.exception.BusinessException;
import com.oilplatform.common.result.Result;
import com.oilplatform.common.utils.MD5Utils;
import com.oilplatform.modules.admin.service.AdminService;
import com.oilplatform.modules.course.entity.Course;
import com.oilplatform.modules.course.entity.Category;
import com.oilplatform.modules.course.mapper.CourseMapper;
import com.oilplatform.modules.course.mapper.CategoryMapper;
import com.oilplatform.modules.user.entity.User;
import com.oilplatform.modules.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CategoryMapper categoryMapper;   // 新增注入

    @Override
    public Result<Page<User>> getUserList(Integer page, Integer size, String keyword, Integer roleId) {
        Page<User> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(User::getUsername, keyword)
                    .or()
                    .like(User::getNickName, keyword);
        }
        if (roleId != null) {
            wrapper.eq(User::getRoleId, roleId);
        }
        wrapper.orderByDesc(User::getCreateTime);
        Page<User> userPage = userMapper.selectPage(pageParam, wrapper);
        userPage.getRecords().forEach(user -> user.setPassword(null));
        return Result.success(userPage);
    }

    @Override
    @Transactional
    public Result<?> createUser(User user) {
        User existUser = userMapper.selectByUsername(user.getUsername());
        if (existUser != null) {
            throw new BusinessException("用户名已存在");
        }
        user.setPassword(MD5Utils.encrypt(user.getPassword()));
        user.setStatus(1);
        userMapper.insert(user);
        return Result.success("用户创建成功");
    }

    @Override
    @Transactional
    public Result<?> updateUser(User user) {
        User existUser = userMapper.selectById(user.getUserId());
        if (existUser == null) {
            throw new BusinessException("用户不存在");
        }
        existUser.setNickName(user.getNickName());
        existUser.setPhone(user.getPhone());
        existUser.setRoleId(user.getRoleId());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existUser.setPassword(MD5Utils.encrypt(user.getPassword()));
        }
        userMapper.updateById(existUser);
        return Result.success("用户信息更新成功");
    }

    @Override
    @Transactional
    public Result<?> disableUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setStatus(0);
        userMapper.updateById(user);
        return Result.success("用户已禁用");
    }

    @Override
    @Transactional
    public Result<?> enableUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setStatus(1);
        userMapper.updateById(user);
        return Result.success("用户已启用");
    }

    @Override
    @Transactional
    public Result<?> deleteUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        userMapper.deleteById(userId);
        return Result.success("用户已删除");
    }

    @Override
    @Transactional
    public Result<?> auditCourse(Long courseId, Integer auditStatus, String reason) {
        Course course = courseMapper.selectById(courseId);
        if (course == null) {
            throw new BusinessException("课程不存在");
        }
        course.setAuditStatus(auditStatus);
        if (auditStatus == 1) {
            course.setPublishStatus(1);
        } else {
            course.setPublishStatus(0);
        }
        courseMapper.updateById(course);
        return Result.success(auditStatus == 1 ? "课程审核通过" : "课程已驳回，原因：" + reason);
    }

    @Override
    public Result<Page<Course>> getPendingCourses(Integer page, Integer size) {
        Page<Course> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getAuditStatus, 0);
        wrapper.orderByDesc(Course::getCreateTime);
        Page<Course> coursePage = courseMapper.selectPage(pageParam, wrapper);
        return Result.success(coursePage);
    }

    // ==================== 分类操作（使用 CategoryMapper） ====================
    @Override
    @Transactional
    public Result<?> addCategory(String categoryName, Long parentId) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setParentId(parentId != null ? parentId : 0L);
        category.setSort(0);
        category.setStatus(1);
        categoryMapper.insert(category);   // 原为 courseMapper.insert
        return Result.success("分类添加成功");
    }

    @Override
    @Transactional
    public Result<?> updateCategory(Long categoryId, String categoryName) {
        Category category = categoryMapper.selectById(categoryId);
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        category.setCategoryName(categoryName);
        categoryMapper.updateById(category); // 原为 courseMapper.updateById
        return Result.success("分类更新成功");
    }

    @Override
    @Transactional
    public Result<?> deleteCategory(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        categoryMapper.deleteById(categoryId); // 原为 courseMapper.deleteById
        return Result.success("分类删除成功");
    }
}