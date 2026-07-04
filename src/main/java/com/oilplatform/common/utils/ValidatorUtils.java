package com.oilplatform.common.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidatorUtils {

    // 常用正则表达式
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{4,20}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*]{6,20}$");
    private static final Pattern URL_PATTERN = Pattern.compile("^(https?|ftp)://[^\\s/$.?#].[^\\s]*$");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+(\\.\\d+)?$");
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^-?\\d+$");
    private static final Pattern POSITIVE_INTEGER_PATTERN = Pattern.compile("^[1-9]\\d*$");

    /**
     * 校验手机号
     * @param phone 手机号
     * @return true 格式正确
     */
    public static boolean isPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * 校验邮箱
     * @param email 邮箱
     * @return true 格式正确
     */
    public static boolean isEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 校验用户名（字母、数字、下划线，4-20位）
     * @param username 用户名
     * @return true 格式正确
     */
    public static boolean isUsername(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        return USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * 校验密码强度（至少包含字母和数字，6-20位，可含特殊字符）
     * @param password 密码
     * @return true 符合强度要求
     */
    public static boolean isPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * 校验URL格式
     * @param url URL字符串
     * @return true 格式正确
     */
    public static boolean isUrl(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        return URL_PATTERN.matcher(url).matches();
    }

    /**
     * 校验是否为数字（整数或小数）
     * @param str 字符串
     * @return true 是数字
     */
    public static boolean isNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return NUMBER_PATTERN.matcher(str).matches();
    }

    /**
     * 校验是否为整数（可带负号）
     * @param str 字符串
     * @return true 是整数
     */
    public static boolean isInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return INTEGER_PATTERN.matcher(str).matches();
    }

    /**
     * 校验是否为正整数
     * @param str 字符串
     * @return true 是正整数
     */
    public static boolean isPositiveInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return POSITIVE_INTEGER_PATTERN.matcher(str).matches();
    }

    /**
     * 校验字符串长度是否在指定范围内
     * @param str 字符串
     * @param min 最小长度（包含）
     * @param max 最大长度（包含）
     * @return true 长度符合
     */
    public static boolean isLengthBetween(String str, int min, int max) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        return length >= min && length <= max;
    }

    /**
     * 校验字符串是否为空或空白
     * @param str 字符串
     * @return true 为空或仅含空白
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 校验字符串是否不为空且不全是空白
     * @param str 字符串
     * @return true 有实际内容
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}