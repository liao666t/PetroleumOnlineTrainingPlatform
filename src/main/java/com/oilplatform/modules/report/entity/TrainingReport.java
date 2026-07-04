package com.oilplatform.modules.report.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("training_report")
public class TrainingReport {

    @TableId(type = IdType.AUTO)
    private Long reportId;

    private Long userId;

    private Long courseId;

    private String title;

    private String content;

    private String attachmentUrl;

    private BigDecimal score;

    private String reviewComment;

    private Integer reviewStatus;

    private Long reviewerId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private LocalDateTime reviewTime;
}