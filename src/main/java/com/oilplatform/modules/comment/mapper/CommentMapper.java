package com.oilplatform.modules.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oilplatform.modules.comment.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 根据课程ID查询评论（正常状态，关联用户信息）
     */
    @Select("SELECT c.comment_id, c.user_id, c.course_id, c.score, c.content, c.status, c.create_time, " +
            "u.nick_name, u.avatar " +
            "FROM comment c LEFT JOIN user u ON c.user_id = u.user_id " +
            "WHERE c.course_id = #{courseId} AND c.status = 1 " +
            "ORDER BY c.create_time DESC")
    List<Map<String, Object>> selectCommentsWithUserByCourseId(Long courseId);

    /**
     * 计算课程平均评分
     */
    @Select("SELECT COALESCE(AVG(score), 0) FROM comment WHERE course_id = #{courseId} AND status = 1")
    Double selectAverageScoreByCourseId(Long courseId);

    /**
     * 查询用户的评论列表
     */
    @Select("SELECT * FROM comment WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<Comment> selectByUserId(Long userId);
}