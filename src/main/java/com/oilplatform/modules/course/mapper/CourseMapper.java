package com.oilplatform.modules.course.mapper;

import com.oilplatform.modules.course.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    @Select("SELECT * FROM course WHERE category_id = #{categoryId} AND publish_status = 1")
    List<Course> selectByCategoryId(Long categoryId);

    @Select("SELECT * FROM course WHERE teacher_id = #{teacherId}")
    List<Course> selectByTeacherId(Long teacherId);
}