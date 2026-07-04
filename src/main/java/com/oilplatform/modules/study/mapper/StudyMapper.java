package com.oilplatform.modules.study.mapper;

import com.oilplatform.modules.study.entity.UserCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudyMapper extends BaseMapper<UserCourse> {
}