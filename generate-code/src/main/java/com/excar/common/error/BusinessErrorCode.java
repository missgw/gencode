package com.excar.common.error;

/**
  *@Description 业务通用异常枚举
  *@Author gambler
**/
public enum BusinessErrorCode {

    /**
     * 通用业务异常
     */
    BUSINESS_ERROR("E99999", "业务异常"),
    RECORD_NOT_FOUND("E50000", "记录不存在"),
    UN_AUTHORIZED("E50001", "权限不足")
    ;

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    BusinessErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
