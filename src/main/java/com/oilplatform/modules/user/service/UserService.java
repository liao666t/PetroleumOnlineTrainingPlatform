package com.oilplatform.modules.user.service;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.user.entity.User;
import java.util.Map;

public interface UserService {

    Result<?> register(User user);

    Result<Map<String, Object>> login(String username, String password);

    Result<?> updateProfile(User user);

    Result<?> updatePassword(Long userId, String oldPassword, String newPassword);

    Result<?> updateAvatar(Long userId, String avatar);

    User getUserById(Long userId);
}