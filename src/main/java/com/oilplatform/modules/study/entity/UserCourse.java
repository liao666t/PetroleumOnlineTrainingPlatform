package com.oilplatform.modules.study.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("user_course")
public class UserCourse {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long courseId;

    private BigDecimal studyProgress;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime selectTime;

    private LocalDateTime lastStudyTime;

    private Integer isFavorite;
}