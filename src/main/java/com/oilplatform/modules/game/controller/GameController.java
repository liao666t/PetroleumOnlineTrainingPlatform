package com.oilplatform.modules.game.controller;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.game.entity.GameLevel;
import com.oilplatform.modules.game.service.GameService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/levels/{courseId}")
    public Result<List<GameLevel>> getLevelsByCourse(@PathVariable Long courseId) {
        return gameService.getLevelsByCourse(courseId);
    }

    @PostMapping("/submit")
    public Result<?> submitAnswer(@RequestBody Map<String, Object> answerData,
                                  HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long levelId = Long.valueOf(answerData.get("levelId").toString());
        String answer = (String) answerData.get("answer");
        return gameService.submitAnswer(userId, levelId, answer);
    }

    @GetMapping("/ranking")
    public Result<List<Map<String, Object>>> getRankingList() {
        return gameService.getRankingList();
    }

    @GetMapping("/score")
    public Result<Integer> getMyScore(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return gameService.getMyScore(userId);
    }

    @GetMapping("/records")
    public Result<List<Map<String, Object>>> getMyRecords(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return gameService.getMyRecords(userId);
    }

    @PostMapping("/level/create")
    public Result<?> createLevel(@RequestBody GameLevel level) {
        return gameService.createLevel(level);
    }

    @PutMapping("/level/update")
    public Result<?> updateLevel(@RequestBody GameLevel level) {
        return gameService.updateLevel(level);
    }

    @DeleteMapping("/level/delete/{levelId}")
    public Result<?> deleteLevel(@PathVariable Long levelId) {
        return gameService.deleteLevel(levelId);
    }
}