package com.lc.springboot.mybatisplus.generatecode.extension;

import lombok.Data;

/**
 * 枚举封装类
 *
 * @author liangchao
 * @version 1.0
 * @date 2021/9/16 6:05 下午
 */
@Data
public class EnumWrapper {

    /**
     * 枚举类名称
     */
    private String enumClassName;

    /**
     * 属性名称(例如：xxxxDesc)
     */
    private String propertyName;
}
