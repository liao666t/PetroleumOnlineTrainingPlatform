package com.oilplatform.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    private static final String SALT = "AmazingTeaching@2024";

    public static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String saltedPassword = password + SALT;
            byte[] bytes = md.digest(saltedPassword.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xff);
                if (hex.length() == 1) {
                    sb.append("0");
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5加密失败", e);
        }
    }

    public static boolean verify(String password, String encryptedPassword) {
        return encrypt(password).equals(encryptedPassword);
    }
}