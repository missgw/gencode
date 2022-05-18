//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.excar.common;

import com.excar.common.error.BusinessErrorCode;
import com.excar.common.error.CommonErrorCode;

public class BusinessException extends RuntimeException {
    private String code;

    public BusinessException() {
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
        this.code = BusinessErrorCode.BUSINESS_ERROR.getCode();
    }

    /**
     * 使用枚举传参
     *
     * @param errorCode 异常枚举
     */
    public BusinessException(BusinessErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    /**
     * 使用CommonErrorCode枚举传参
     *
     * @param errorCode 异常枚举
     */
    public BusinessException(CommonErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }



    public String getCode() {
        return this.code;
    }
}
