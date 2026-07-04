package com.oilplatform.modules.system.mapper;

import com.oilplatform.modules.system.entity.OperLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemMapper extends BaseMapper<OperLog> {
}