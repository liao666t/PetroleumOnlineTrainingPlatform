package com.oilplatform.modules.user.controller;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.user.entity.User;
import com.oilplatform.modules.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> loginMap) {
        String username = loginMap.get("username");
        String password = loginMap.get("password");
        return userService.login(username, password);
    }

    @PutMapping("/profile")
    public Result<?> updateProfile(@RequestBody User user, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        user.setUserId(userId);
        return userService.updateProfile(user);
    }

    @PutMapping("/password")
    public Result<?> updatePassword(@RequestBody Map<String, String> passwordMap,
                                    HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String oldPassword = passwordMap.get("oldPassword");
        String newPassword = passwordMap.get("newPassword");
        return userService.updatePassword(userId, oldPassword, newPassword);
    }

    @PutMapping("/avatar")
    public Result<?> updateAvatar(@RequestBody Map<String, String> avatarMap,
                                  HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String avatar = avatarMap.get("avatar");
        return userService.updateAvatar(userId, avatar);
    }

    @GetMapping("/info")
    public Result<User> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        User user = userService.getUserById(userId);
        user.setPassword(null);
        return Result.success(user);
    }
}