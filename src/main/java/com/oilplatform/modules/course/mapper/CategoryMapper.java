package com.oilplatform.modules.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oilplatform.modules.course.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    @Select("SELECT * FROM category WHERE parent_id = #{parentId} AND status = 1 ORDER BY sort")
    List<Category> selectByParentId(Long parentId);
}