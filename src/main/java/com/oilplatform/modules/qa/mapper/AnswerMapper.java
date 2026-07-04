package com.oilplatform.modules.qa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oilplatform.modules.qa.entity.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface AnswerMapper extends BaseMapper<Answer> {

    @Select("SELECT * FROM answer WHERE question_id = #{questionId} ORDER BY is_accepted DESC, create_time ASC")
    List<Answer> selectByQuestionId(Long questionId);
}