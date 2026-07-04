package com.oilplatform.modules.game.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("user_game_record")
public class UserGameRecord {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long levelId;

    private String userAnswer;

    private Integer result;

    private Integer gainScore;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}