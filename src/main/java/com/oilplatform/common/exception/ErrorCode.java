package com.oilplatform.common.exception;

public enum ErrorCode {

    SUCCESS(200, "操作成功"),
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_ERROR(500, "服务器内部错误"),

    USER_NOT_EXIST(1001, "用户不存在"),
    PASSWORD_ERROR(1002, "密码错误"),
    ACCOUNT_DISABLED(1003, "账号已被禁用"),
    TOKEN_EXPIRED(1004, "Token已过期"),

    COURSE_NOT_EXIST(2001, "课程不存在"),
    COURSE_ALREADY_ENROLLED(2002, "已选该课程"),

    REPORT_NOT_EXIST(3001, "实训报告不存在"),
    REPORT_ALREADY_REVIEWED(3002, "报告已批阅"),

    LEVEL_NOT_EXIST(4001, "闯关关卡不存在"),

    QUESTION_NOT_EXIST(5001, "问题不存在"),

    AI_SERVICE_ERROR(6001, "AI服务异常");

    private Integer code;
    private String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}