package com.oilplatform.modules.report.mapper;

import com.oilplatform.modules.report.entity.TrainingReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ReportMapper extends BaseMapper<TrainingReport> {

    @Select("SELECT * FROM training_report WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<TrainingReport> selectByUserId(Long userId);

    @Select("SELECT * FROM training_report WHERE course_id = #{courseId} AND review_status = 0")
    List<TrainingReport> selectUnreviewedByCourseId(Long courseId);

    @Select("SELECT * FROM training_report WHERE reviewer_id = #{reviewerId} ORDER BY review_time DESC")
    List<TrainingReport> selectByReviewerId(Long reviewerId);
}