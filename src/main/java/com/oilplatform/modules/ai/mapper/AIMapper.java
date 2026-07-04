package com.oilplatform.modules.ai.mapper;

import com.oilplatform.modules.ai.entity.AIChat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface AIMapper extends BaseMapper<AIChat> {

    @Select("SELECT * FROM ai_chat WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<AIChat> selectByUserId(Long userId);

    @Select("SELECT DISTINCT knowledge_point FROM ai_chat WHERE user_id = #{userId} AND knowledge_point IS NOT NULL")
    List<String> selectKnowledgePoints(Long userId);
}