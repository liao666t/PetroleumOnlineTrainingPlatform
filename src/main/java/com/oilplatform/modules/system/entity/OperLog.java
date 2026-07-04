package com.oilplatform.modules.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_oper_log")
public class OperLog {

    @TableId(type = IdType.AUTO)
    private Long logId;

    private Long userId;

    private String operContent;

    private String operType;

    private String operIp;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime operTime;

    private String requestMethod;

    private String requestUrl;

    private String requestParams;
}