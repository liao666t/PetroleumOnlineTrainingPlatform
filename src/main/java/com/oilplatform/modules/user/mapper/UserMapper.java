package com.oilplatform.modules.user.mapper;

import com.oilplatform.modules.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(String username);

    @Select("SELECT * FROM user WHERE phone = #{phone}")
    User selectByPhone(String phone);
}