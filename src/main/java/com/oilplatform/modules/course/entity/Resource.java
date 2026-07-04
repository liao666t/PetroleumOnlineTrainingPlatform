package com.oilplatform.modules.course.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("resource")
public class Resource {

    @TableId(type = IdType.AUTO)
    private Long resId;

    private Long chapterId;

    private String resType;

    private String resName;

    private String fileUrl;

    private Long fileSize;

    private Integer duration;
}