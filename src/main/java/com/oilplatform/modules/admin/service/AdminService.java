package com.oilplatform.modules.admin.service;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.user.entity.User;
import com.oilplatform.modules.course.entity.Course;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface AdminService {

    Result<Page<User>> getUserList(Integer page, Integer size, String keyword, Integer roleId);

    Result<?> createUser(User user);

    Result<?> updateUser(User user);

    Result<?> disableUser(Long userId);

    Result<?> enableUser(Long userId);

    Result<?> deleteUser(Long userId);

    Result<?> auditCourse(Long courseId, Integer auditStatus, String reason);

    Result<Page<Course>> getPendingCourses(Integer page, Integer size);

    Result<?> addCategory(String categoryName, Long parentId);

    Result<?> updateCategory(Long categoryId, String categoryName);

    Result<?> deleteCategory(Long categoryId);
}