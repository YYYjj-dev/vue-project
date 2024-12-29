package com.test.common;

public enum ResultCodeEnum {
    SUCCESS(200,"success"),
    ADDITION_FAILED(401,"additionFailed"),
    DELETION_FAILED(402,"deletionFailed"),
    UPDATE_FAILED(403,"updateFailed"),
    NOT_FOUND(404,"fileNotFound"),
    USERNAME_ERROR(501,"usernameError"),
    PASSWORD_ERROR(503,"passwordError"),
    NOT_LOGIN(504,"notLogin"),
    USERNAME_USED(505,"userNameUsed"),
    UPDATE_USER_FAILED(506,"updateUserFailed"),
    USERTYPE_ERROR(507,"userTypeError"),
    ;

    private Integer code;
    private String message;
    ResultCodeEnum(Integer code, String message) {
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
