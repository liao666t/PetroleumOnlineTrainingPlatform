package com.oilplatform.modules.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oilplatform.modules.game.entity.UserGameRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserGameRecordMapper extends BaseMapper<UserGameRecord> {

    @Select("SELECT * FROM user_game_record WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<UserGameRecord> selectRecordsByUserId(Long userId);

    @Select("SELECT COALESCE(SUM(gain_score), 0) FROM user_game_record WHERE user_id = #{userId}")
    Integer selectTotalScoreByUserId(Long userId);

    @Select("SELECT u.nick_name, COALESCE(SUM(ugr.gain_score), 0) as total_score " +
            "FROM user_game_record ugr " +
            "LEFT JOIN user u ON ugr.user_id = u.user_id " +
            "GROUP BY ugr.user_id, u.nick_name " +
            "ORDER BY total_score DESC " +
            "LIMIT 50")
    List<Map<String, Object>> selectRankingList();
}