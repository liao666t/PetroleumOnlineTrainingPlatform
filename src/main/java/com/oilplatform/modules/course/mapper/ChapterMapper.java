package com.oilplatform.modules.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oilplatform.modules.course.entity.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ChapterMapper extends BaseMapper<Chapter> {

    @Select("SELECT * FROM chapter WHERE course_id = #{courseId} ORDER BY sort")
    List<Chapter> selectByCourseId(Long courseId);
}