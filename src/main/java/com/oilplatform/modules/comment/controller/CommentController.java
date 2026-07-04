package com.oilplatform.modules.comment.controller;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.comment.entity.Comment;
import com.oilplatform.modules.comment.service.CommentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /** 添加评论（学生） */
    @PostMapping("/add")
    public Result<?> addComment(@RequestBody Comment comment, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        comment.setUserId(userId);
        return commentService.addComment(comment);
    }

    /** 获取课程评论列表 */
    @GetMapping("/list/{courseId}")
    public Result<List<Map<String, Object>>> getCommentsByCourse(@PathVariable Long courseId) {
        return commentService.getCommentsByCourse(courseId);
    }

    /** 删除自己的评论 */
    @DeleteMapping("/delete/{commentId}")
    public Result<?> deleteMyComment(@PathVariable Long commentId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return commentService.deleteMyComment(commentId, userId);
    }

    /** 我的评论列表 */
    @GetMapping("/my")
    public Result<Page<Comment>> getMyComments(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Long userId = (Long) request.getAttribute("userId");
        return commentService.getMyComments(userId, page, size);
    }

    /** 屏蔽评论（管理员/教师） */
    @PutMapping("/hide/{commentId}")
    public Result<?> hideComment(@PathVariable Long commentId) {
        return commentService.hideComment(commentId);
    }

    /** 恢复评论（管理员/教师） */
    @PutMapping("/show/{commentId}")
    public Result<?> showComment(@PathVariable Long commentId) {
        return commentService.showComment(commentId);
    }

    /** 课程平均评分 */
    @GetMapping("/average/{courseId}")
    public Result<Double> getCourseAverageScore(@PathVariable Long courseId) {
        return commentService.getCourseAverageScore(courseId);
    }
}