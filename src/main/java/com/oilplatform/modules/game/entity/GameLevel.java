package com.oilplatform.modules.game.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("game_level")
public class GameLevel {

    @TableId(type = IdType.AUTO)
    private Long levelId;

    private Long courseId;

    private String levelName;

    private String questionContent;

    private String questionType;

    private String correctAnswer;

    private String options;

    private Integer rewardScore;

    private Integer difficulty;

    private String knowledgePoint;
}