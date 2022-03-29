package com.qjdchina.saas.common.web;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据库表公共字段
 *
 * @author liangchao
 */
@Data
public class BaseModel implements Serializable {

    /**
     * 数据库字段
     */
    public static final String COL_ID = "ID";
    public static final String COL_CREATED_BY = "CREATED_BY";
    public static final String COL_CREATED_TIME = "CREATED_TIME";
    public static final String COL_UPDATED_TIME = "UPDATED_TIME";
    public static final String COL_DELETE_FLAG = "DELETE_FLAG";

    /**
     * 类属性名
     */
    public static final String ID = "id";
    public static final String CREATED_BY = "createdBy";
    public static final String CREATED_TIME = "createdTime";
    public static final String UPDATED_TIME = "updatedTime";
    public static final String DELETE_FLAG = "deleteFlag";

    /**
     * id 主键
     */
    @Id
    @GeneratedValue
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id-主键")
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间 格式yyyyMMddHHmmss")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private Date createdTime;

    /**
     * 修改时间
     */
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间 格式yyyyMMddHHmmss")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private Date updatedTime;

    /**
     * 删除标记 0:未删除 1：已删除 逻辑删除
     */
    @TableField("delete_flag")
    @ApiModelProperty(value = "逻辑删除标记 0:未删除（默认） 1：已删除")
    @TableLogic
    private Integer deleteFlag;

    /**
     * 创建人
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人")
    private String createdBy;
}
