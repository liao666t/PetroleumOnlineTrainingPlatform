package com.oilplatform.modules.qa.mapper;

import com.oilplatform.modules.qa.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface QAMapper extends BaseMapper<Question> {

    @Select("SELECT * FROM question WHERE course_id = #{courseId} ORDER BY is_top DESC, create_time DESC")
    List<Question> selectQuestionsByCourseId(Long courseId);

    @Select("SELECT * FROM question WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<Question> selectQuestionsByUserId(Long userId);
}