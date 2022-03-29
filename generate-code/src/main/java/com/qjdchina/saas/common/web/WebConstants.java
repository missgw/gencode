package com.qjdchina.saas.common.web;

/**
 * Web应用共通的常量

 * @author zhaofeng
 */
public interface WebConstants {

    String REQUEST_REMOTE_HOST_MDC_KEY = "req.remoteHost";
    String REQUEST_USER_AGENT_MDC_KEY = "req.userAgent";
    String REQUEST_REQUEST_URI = "req.requestURI";
    String REQUEST_QUERY_STRING = "req.queryString";
    String REQUEST_REQUEST_URL = "req.requestURL";
    String REQUEST_METHOD = "req.method";
    String REQUEST_X_FORWARDED_FOR = "req.xForwardedFor";

    String REQUEST_TERMINAL_ID = "x-terminal-id";
    /**
     * 请求唯一标识
     */
    String HEADER_REQUEST_ID = "x-request-id";
    String HEADER_TENANT_ID = "x-tenant-id";
    String HEADER_MOBILE = "x-captcha-mobile";
    String HEADER_SMS_CODE = "x-captcha-sms-code";
    String HEADER_PIC_KEY = "x-captcha-pic-key";
    String HEADER_PIC_CODE = "x-captcha-pic-code";
}
