package com.oilplatform.modules.comment.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("comment")
public class Comment {

    @TableId(type = IdType.AUTO)
    private Long commentId;

    private Long userId;

    private Long courseId;

    private BigDecimal score;

    private String content;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}