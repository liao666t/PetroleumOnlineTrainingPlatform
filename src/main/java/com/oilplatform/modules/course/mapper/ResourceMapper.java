package com.oilplatform.modules.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oilplatform.modules.course.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

    @Select("SELECT * FROM resource WHERE chapter_id = #{chapterId}")
    List<Resource> selectByChapterId(Long chapterId);
}