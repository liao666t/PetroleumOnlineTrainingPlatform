package com.oilplatform.modules.statistics.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface StatisticsMapper {

    @Select("SELECT COUNT(*) FROM user WHERE role_id = 1 AND status = 1")
    Long countStudents();

    @Select("SELECT COUNT(*) FROM user WHERE role_id = 2 AND status = 1")
    Long countTeachers();

    @Select("SELECT COUNT(*) FROM course WHERE publish_status = 1 AND audit_status = 1")
    Long countCourses();

    @Select("SELECT COUNT(*) FROM training_report")
    Long countReports();

    @Select("SELECT c.category_name, COUNT(co.course_id) as count " +
            "FROM category c LEFT JOIN course co ON c.category_id = co.category_id " +
            "WHERE c.parent_id = 0 " +
            "GROUP BY c.category_id, c.category_name")
    List<Map<String, Object>> countCoursesByCategory();

    @Select("SELECT DATE_FORMAT(create_time, '%Y-%m') as month, COUNT(*) as count " +
            "FROM user WHERE role_id = 1 " +
            "GROUP BY DATE_FORMAT(create_time, '%Y-%m') " +
            "ORDER BY month DESC LIMIT 12")
    List<Map<String, Object>> studentRegisterTrend();

    @Select("SELECT DATE_FORMAT(create_time, '%Y-%m-%d') as date, COUNT(*) as count " +
            "FROM ai_chat " +
            "GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d') " +
            "ORDER BY date DESC LIMIT 30")
    List<Map<String, Object>> aiUsageTrend();

    @Select("SELECT c.course_name, COUNT(uc.id) as student_count, " +
            "AVG(uc.study_progress) as avg_progress " +
            "FROM course c " +
            "LEFT JOIN user_course uc ON c.course_id = uc.course_id " +
            "WHERE c.publish_status = 1 " +
            "GROUP BY c.course_id, c.course_name " +
            "ORDER BY student_count DESC LIMIT 10")
    List<Map<String, Object>> coursePopularity();

    @Select("SELECT u.nick_name, COALESCE(SUM(ugr.gain_score), 0) as total_score, " +
            "COUNT(ugr.id) as answer_count " +
            "FROM user u " +
            "LEFT JOIN user_game_record ugr ON u.user_id = ugr.user_id " +
            "WHERE u.role_id = 1 " +
            "GROUP BY u.user_id, u.nick_name " +
            "ORDER BY total_score DESC LIMIT 20")
    List<Map<String, Object>> studentRanking();
}