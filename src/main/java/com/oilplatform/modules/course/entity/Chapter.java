package com.oilplatform.modules.course.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("chapter")
public class Chapter {

    @TableId(type = IdType.AUTO)
    private Long chapterId;

    private Long courseId;

    private String chapterName;

    private Integer sort;

    private String description;
}