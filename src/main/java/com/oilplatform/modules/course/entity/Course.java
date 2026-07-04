package com.oilplatform.modules.course.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("course")
public class Course {

    @TableId(type = IdType.AUTO)
    private Long courseId;

    private Long categoryId;

    private Long teacherId;

    private String courseName;

    private String cover;

    private String intro;

    private BigDecimal score;

    private Integer auditStatus;

    private Integer publishStatus;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}