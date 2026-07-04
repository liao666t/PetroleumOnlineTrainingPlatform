package com.oilplatform.modules.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("menu")
public class Menu {

    @TableId(type = IdType.AUTO)
    private Long menuId;

    private Long parentId;

    private String menuName;

    private String route;

    private String permissionKey;

    private Integer sort;

    private String icon;
}