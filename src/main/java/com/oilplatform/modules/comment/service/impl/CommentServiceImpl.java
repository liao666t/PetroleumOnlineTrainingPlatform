package com.oilplatform.modules.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oilplatform.common.exception.BusinessException;
import com.oilplatform.common.result.Result;
import com.oilplatform.modules.comment.entity.Comment;
import com.oilplatform.modules.comment.mapper.CommentMapper;
import com.oilplatform.modules.comment.service.CommentService;
import com.oilplatform.modules.study.entity.UserCourse;
import com.oilplatform.modules.study.mapper.StudyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private StudyMapper studyMapper;

    @Override
    @Transactional
    public Result<?> addComment(Comment comment) {
        // 校验是否已选课
        LambdaQueryWrapper<UserCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCourse::getUserId, comment.getUserId());
        wrapper.eq(UserCourse::getCourseId, comment.getCourseId());
        if (studyMapper.selectCount(wrapper) == 0) {
            throw new BusinessException("请先选课后再进行评价");
        }

        // 防止重复评论
        LambdaQueryWrapper<Comment> commentWrapper = new LambdaQueryWrapper<>();
        commentWrapper.eq(Comment::getUserId, comment.getUserId());
        commentWrapper.eq(Comment::getCourseId, comment.getCourseId());
        if (commentMapper.selectCount(commentWrapper) > 0) {
            throw new BusinessException("您已经评价过该课程");
        }

        comment.setStatus(1);
        comment.setCreateTime(LocalDateTime.now());
        commentMapper.insert(comment);
        return Result.success("评论成功");
    }

    @Override
    public Result<List<Map<String, Object>>> getCommentsByCourse(Long courseId) {
        List<Map<String, Object>> comments = commentMapper.selectCommentsWithUserByCourseId(courseId);
        return Result.success(comments);
    }

    @Override
    @Transactional
    public Result<?> deleteMyComment(Long commentId, Long userId) {
        Comment comment = commentMapper.selectById(commentId);
        if (comment == null) {
            throw new BusinessException("评论不存在");
        }
        if (!comment.getUserId().equals(userId)) {
            throw new BusinessException("无权删除他人评论");
        }
        commentMapper.deleteById(commentId);
        return Result.success("评论已删除");
    }

    @Override
    public Result<Page<Comment>> getMyComments(Long userId, Integer page, Integer size) {
        Page<Comment> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getUserId, userId);
        wrapper.orderByDesc(Comment::getCreateTime);
        Page<Comment> commentPage = commentMapper.selectPage(pageParam, wrapper);
        return Result.success(commentPage);
    }

    @Override
    @Transactional
    public Result<?> hideComment(Long commentId) {
        Comment comment = commentMapper.selectById(commentId);
        if (comment == null) {
            throw new BusinessException("评论不存在");
        }
        comment.setStatus(0);
        commentMapper.updateById(comment);
        return Result.success("评论已屏蔽");
    }

    @Override
    @Transactional
    public Result<?> showComment(Long commentId) {
        Comment comment = commentMapper.selectById(commentId);
        if (comment == null) {
            throw new BusinessException("评论不存在");
        }
        comment.setStatus(1);
        commentMapper.updateById(comment);
        return Result.success("评论已恢复");
    }

    @Override
    public Result<Double> getCourseAverageScore(Long courseId) {
        Double avgScore = commentMapper.selectAverageScoreByCourseId(courseId);
        return Result.success(avgScore != null ? avgScore : 0.0);
    }
}