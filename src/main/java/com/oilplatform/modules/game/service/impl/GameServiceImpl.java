package com.oilplatform.modules.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oilplatform.common.exception.BusinessException;
import com.oilplatform.common.result.Result;
import com.oilplatform.modules.game.entity.GameLevel;
import com.oilplatform.modules.game.entity.UserGameRecord;
import com.oilplatform.modules.game.mapper.GameMapper;
import com.oilplatform.modules.game.mapper.UserGameRecordMapper;
import com.oilplatform.modules.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private UserGameRecordMapper userGameRecordMapper;

    @Override
    public Result<List<GameLevel>> getLevelsByCourse(Long courseId) {
        List<GameLevel> levels = gameMapper.selectByCourseId(courseId);
        return Result.success(levels);
    }

    @Override
    @Transactional
    public Result<?> submitAnswer(Long userId, Long levelId, String answer) {
        GameLevel level = gameMapper.selectById(levelId);
        if (level == null) {
            throw new BusinessException("闯关关卡不存在");
        }

        int result = 0;
        int gainScore = 0;

        if (answer != null && answer.equals(level.getCorrectAnswer())) {
            result = 1;
            gainScore = level.getRewardScore();
        }

        UserGameRecord record = new UserGameRecord();
        record.setUserId(userId);
        record.setLevelId(levelId);
        record.setUserAnswer(answer);
        record.setResult(result);
        record.setGainScore(gainScore);
        record.setCreateTime(LocalDateTime.now());

        userGameRecordMapper.insert(record);

        if (result == 1) {
            return Result.success("回答正确！获得" + gainScore + "积分");
        } else {
            return Result.error("回答错误，继续加油！");
        }
    }

    @Override
    public Result<List<Map<String, Object>>> getRankingList() {
        List<Map<String, Object>> rankingList = userGameRecordMapper.selectRankingList();
        return Result.success(rankingList);
    }

    @Override
    public Result<Integer> getMyScore(Long userId) {
        Integer totalScore = userGameRecordMapper.selectTotalScoreByUserId(userId);
        return Result.success(totalScore);
    }

    @Override
    public Result<List<Map<String, Object>>> getMyRecords(Long userId) {
        List<UserGameRecord> records = userGameRecordMapper.selectRecordsByUserId(userId);
        List<Map<String, Object>> result = records.stream().map(record -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", record.getId());
            map.put("levelId", record.getLevelId());
            map.put("userAnswer", record.getUserAnswer());
            map.put("result", record.getResult());
            map.put("gainScore", record.getGainScore());
            map.put("createTime", record.getCreateTime());
            return map;
        }).collect(Collectors.toList());
        return Result.success(result);
    }

    @Override
    @Transactional
    public Result<?> createLevel(GameLevel level) {
        gameMapper.insert(level);
        return Result.success("关卡创建成功");
    }

    @Override
    @Transactional
    public Result<?> updateLevel(GameLevel level) {
        GameLevel existLevel = gameMapper.selectById(level.getLevelId());
        if (existLevel == null) {
            throw new BusinessException("关卡不存在");
        }
        gameMapper.updateById(level);
        return Result.success("关卡更新成功");
    }

    @Override
    @Transactional
    public Result<?> deleteLevel(Long levelId) {
        GameLevel level = gameMapper.selectById(levelId);
        if (level == null) {
            throw new BusinessException("关卡不存在");
        }
        // 先删除该关卡下的所有用户答题记录
        LambdaQueryWrapper<UserGameRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserGameRecord::getLevelId, levelId);
        userGameRecordMapper.delete(wrapper);
        // 再删除关卡本身
        gameMapper.deleteById(levelId);
        return Result.success("关卡删除成功");
    }
}