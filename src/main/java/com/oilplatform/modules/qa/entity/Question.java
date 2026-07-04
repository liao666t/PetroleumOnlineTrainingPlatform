package com.oilplatform.modules.qa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("question")
public class Question {

    @TableId(type = IdType.AUTO)
    private Long questionId;

    private Long userId;

    private Long courseId;

    private String title;

    private String content;

    private Integer status;

    private Integer isTop;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}