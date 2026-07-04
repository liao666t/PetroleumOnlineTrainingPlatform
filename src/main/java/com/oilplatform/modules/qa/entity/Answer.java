package com.oilplatform.modules.qa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("answer")
public class Answer {

    @TableId(type = IdType.AUTO)
    private Long answerId;

    private Long questionId;

    private Long userId;

    private String content;

    private Integer isAccepted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}