package com.qjdchina.saas.common.error;

import org.springframework.http.HttpStatus;

/**
  *@Description 异常枚举
  *@Author gambler
  *@Date 2021/1/7 5:38 PM
  *@Version 1.0
**/
public enum CommonErrorCode {

    /**
     * 404 Web 服务器找不到您所请求的文件或脚本。请检查URL 以确保路径正确。
     */
    NOT_FOUND("S404",
            String.format("哎呀，无法找到这个资源啦(%s)", HttpStatus.NOT_FOUND.getReasonPhrase())),


    /**
     * 415 Unsupported Media Type
     */
    UNSUPPORTED_MEDIA_TYPE("S415",
            String.format("不支持该媒体类型(%s)", HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())),


    /**
     * 参数错误
     */
    PARAM_ERROR("S100", "参数错误"),


    /**
     * 系统异常 500 服务器的内部错误
     */
    EXCEPTION("S500", "服务器开小差，请稍后再试"),


    /**
     * 业务异常
     */
    BUSINESS_ERROR("S400", "业务异常"),

    /**
     * rpc调用异常
     */
    RPC_ERROR("RPC-510", "呀，网络出问题啦！");


    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    CommonErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
