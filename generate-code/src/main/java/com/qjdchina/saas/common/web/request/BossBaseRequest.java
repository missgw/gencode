package com.qjdchina.saas.common.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Boss模块数据库表公共字段
 *
 * @author liangchao
 */
@Data
public class BossBaseRequest extends BaseRequest implements Serializable {

    /**
     * 组织编号
     */
    @ApiModelProperty(value = "组织编号", example = "0", hidden = true)
    private Long orgId;
}
