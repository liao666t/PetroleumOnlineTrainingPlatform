package com.oilplatform.modules.ai.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("ai_chat")
public class AIChat {

    @TableId(type = IdType.AUTO)
    private Long chatId;

    private Long userId;

    private String userQuestion;

    private String aiAnswer;

    private String knowledgePoint;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}