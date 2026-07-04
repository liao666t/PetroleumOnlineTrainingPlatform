package com.oilplatform.modules.course.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("category")
public class Category {

    @TableId(type = IdType.AUTO)
    private Long categoryId;

    private Long parentId;

    private String categoryName;

    private Integer sort;

    private Integer status;
}