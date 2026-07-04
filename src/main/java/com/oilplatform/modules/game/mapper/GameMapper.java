package com.oilplatform.modules.game.mapper;

import com.oilplatform.modules.game.entity.GameLevel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface GameMapper extends BaseMapper<GameLevel> {

    @Select("SELECT * FROM game_level WHERE course_id = #{courseId} ORDER BY difficulty")
    List<GameLevel> selectByCourseId(Long courseId);
}