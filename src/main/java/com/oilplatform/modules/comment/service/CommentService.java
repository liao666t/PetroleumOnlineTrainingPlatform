package com.oilplatform.modules.comment.service;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.comment.entity.Comment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.Map;

public interface CommentService {

    //添加课程评论
    Result<?> addComment(Comment comment);

    //获取课程评论列表
    Result<List<Map<String, Object>>> getCommentsByCourse(Long courseId);

    //删除自己的评论
    Result<?> deleteMyComment(Long commentId, Long userId);

    //获取评论列表
    Result<Page<Comment>> getMyComments(Long userId, Integer page, Integer size);

    // 管理员/教师屏蔽违规评论
    Result<?> hideComment(Long commentId);

    //管理员/教师恢复评论
    Result<?> showComment(Long commentId);

    //获取课程平均评分
    Result<Double> getCourseAverageScore(Long courseId);
}