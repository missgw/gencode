package com.excar.common.web;

import lombok.Getter;
import lombok.Setter;

/**
 * 服务返回统一对象封装类
 *
 * @author zhaofeng
 */
@Getter
public class RestBody<T> {
    private String code;
    private String message;
    @Setter
    private Object detail;
    @Setter
    private T data;

    public static <R> RestBody<R> success(R data) {
        RestBody<R> result = RestBody.success();
        result.code = "0";
        result.message = "成功";
        result.data = data;
        return result;
    }

    public static <R> RestBody<R> success() {
        RestBody<R> result = RestBody.success();
        result.code = "0";
        result.message = "成功";
        return result;
    }

    public static <R> RestBody<R> fail(String code, String message) {
        RestBody<R> result = RestBody.success();
        result.code = code;
        result.message = message;
        return result;
    }

    public static <R> RestBody<R> fail(String message) {
        RestBody<R> result = RestBody.success();
        result.code = "E";
        result.message = message;
        return result;
    }

    public static <R> RestBody<R> fail() {
        RestBody<R> result = RestBody.success();
        result.code = "E";
        result.message = "操作失败";
        return result;
    }

//    public static <R> RestBody<R> fail(CommonErrorCode resultEnum) {
//        RestBody<R> result = RestBody.success();
//        result.code = resultEnum.getCode();
//        result.message = resultEnum.getMessage();
//        return result;
//    }

}
