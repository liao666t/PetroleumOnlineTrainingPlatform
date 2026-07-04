package com.oilplatform.modules.game.service;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.game.entity.GameLevel;
import java.util.List;
import java.util.Map;

public interface GameService {

    Result<List<GameLevel>> getLevelsByCourse(Long courseId);

    Result<?> submitAnswer(Long userId, Long levelId, String answer);

    Result<List<Map<String, Object>>> getRankingList();

    Result<Integer> getMyScore(Long userId);

    Result<List<Map<String, Object>>> getMyRecords(Long userId);

    Result<?> createLevel(GameLevel level);

    Result<?> updateLevel(GameLevel level);

    Result<?> deleteLevel(Long levelId);
}