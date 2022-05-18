package com.excar.common.web;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Boss模块数据库表公共字段
 *
 * @author gewei
 */
@Data
public class BossBaseModel extends BaseModel implements Serializable {

    /**
     * 数据库字段
     */
    public static final String COL_ORG_ID = "ORG_ID";

    /**
     * 类属性名
     */
    public static final String ORG_ID = "orgId";

    /**
     * 组织编号
     */
    @TableField(value = "org_id", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "组织编号")
    private Long orgId;
}
