package com.oilplatform.modules.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("role")
public class Role {

    @TableId(type = IdType.AUTO)
    private Integer roleId;

    private String roleName;

    private String roleKey;

    private String description;
}