package com.oilplatform.modules.admin.controller;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.admin.service.AdminService;
import com.oilplatform.modules.course.entity.Course;
import com.oilplatform.modules.user.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public Result<Page<User>> getUserList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer roleId) {
        return adminService.getUserList(page, size, keyword, roleId);
    }

    @PostMapping("/user/create")
    public Result<?> createUser(@RequestBody User user) {
        return adminService.createUser(user);
    }

    @PutMapping("/user/update")
    public Result<?> updateUser(@RequestBody User user) {
        return adminService.updateUser(user);
    }

    @PutMapping("/user/disable/{userId}")
    public Result<?> disableUser(@PathVariable Long userId) {
        return adminService.disableUser(userId);
    }

    @PutMapping("/user/enable/{userId}")
    public Result<?> enableUser(@PathVariable Long userId) {
        return adminService.enableUser(userId);
    }

    @DeleteMapping("/user/delete/{userId}")
    public Result<?> deleteUser(@PathVariable Long userId) {
        return adminService.deleteUser(userId);
    }

    @PutMapping("/course/audit/{courseId}")
    public Result<?> auditCourse(@PathVariable Long courseId,
                                 @RequestBody Map<String, Object> auditData) {
        Integer auditStatus = Integer.valueOf(auditData.get("auditStatus").toString());
        String reason = (String) auditData.getOrDefault("reason", "");
        return adminService.auditCourse(courseId, auditStatus, reason);
    }

    @GetMapping("/courses/pending")
    public Result<Page<Course>> getPendingCourses(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return adminService.getPendingCourses(page, size);
    }

    @PostMapping("/category/add")
    public Result<?> addCategory(@RequestBody Map<String, Object> categoryData) {
        String categoryName = (String) categoryData.get("categoryName");
        Long parentId = categoryData.get("parentId") != null ?
                Long.valueOf(categoryData.get("parentId").toString()) : null;
        return adminService.addCategory(categoryName, parentId);
    }

    @PutMapping("/category/update/{categoryId}")
    public Result<?> updateCategory(@PathVariable Long categoryId,
                                    @RequestBody Map<String, String> data) {
        String categoryName = data.get("categoryName");
        return adminService.updateCategory(categoryId, categoryName);
    }

    @DeleteMapping("/category/delete/{categoryId}")
    public Result<?> deleteCategory(@PathVariable Long categoryId) {
        return adminService.deleteCategory(categoryId);
    }
}