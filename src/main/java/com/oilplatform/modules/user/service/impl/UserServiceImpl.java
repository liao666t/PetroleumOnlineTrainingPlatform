package com.oilplatform.modules.user.service.impl;

import com.oilplatform.common.exception.BusinessException;
import com.oilplatform.common.result.Result;
import com.oilplatform.common.utils.JwtUtils;
import com.oilplatform.common.utils.MD5Utils;
import com.oilplatform.modules.user.entity.User;
import com.oilplatform.modules.user.mapper.UserMapper;
import com.oilplatform.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    @Transactional
    public Result<?> register(User user) {
        User existUser = userMapper.selectByUsername(user.getUsername());
        if (existUser != null) {
            throw new BusinessException("用户名已存在");
        }

        if (user.getPhone() != null) {
            User existPhone = userMapper.selectByPhone(user.getPhone());
            if (existPhone != null) {
                throw new BusinessException("手机号已被注册");
            }
        }

        user.setPassword(MD5Utils.encrypt(user.getPassword()));

        if (user.getRoleId() == null) {
            user.setRoleId(1);
        }

        if (user.getAvatar() == null) {
            user.setAvatar("default-avatar.png");
        }

        user.setStatus(1);

        userMapper.insert(user);

        return Result.success("注册成功");
    }

    @Override
    public Result<Map<String, Object>> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用，请联系管理员");
        }

        if (!MD5Utils.verify(password, user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        String token = jwtUtils.generateToken(user.getUserId(), user.getUsername(), user.getRoleId());

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userId", user.getUserId());
        result.put("username", user.getUsername());
        result.put("nickName", user.getNickName());
        result.put("roleId", user.getRoleId());
        result.put("avatar", user.getAvatar());

        return Result.success("登录成功", result);
    }

    @Override
    @Transactional
    public Result<?> updateProfile(User user) {
        User existUser = userMapper.selectById(user.getUserId());
        if (existUser == null) {
            throw new BusinessException("用户不存在");
        }

        existUser.setNickName(user.getNickName());
        existUser.setPhone(user.getPhone());

        userMapper.updateById(existUser);

        return Result.success("个人信息更新成功");
    }

    @Override
    @Transactional
    public Result<?> updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        if (!MD5Utils.verify(oldPassword, user.getPassword())) {
            throw new BusinessException("原密码错误");
        }

        if (MD5Utils.verify(newPassword, user.getPassword())) {
            throw new BusinessException("新密码不能与原密码相同");
        }

        user.setPassword(MD5Utils.encrypt(newPassword));
        userMapper.updateById(user);

        return Result.success("密码修改成功");
    }

    @Override
    @Transactional
    public Result<?> updateAvatar(Long userId, String avatar) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        user.setAvatar(avatar);
        userMapper.updateById(user);

        return Result.success("头像更新成功");
    }

    @Override
    public User getUserById(Long userId) {
        return userMapper.selectById(userId);
    }
}