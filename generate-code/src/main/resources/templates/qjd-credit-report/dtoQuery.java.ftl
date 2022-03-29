package ${package.AddRequest};

import com.qjdchina.creditreport.common.web.CreditReportBasePageRequest;
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
import java.io.Serializable;
<#-- ----------  BEGIN 检测是否存在小数点类型  ---------->
<#list table.fields as field>
    <#if field.columnType== "BIG_DECIMAL">
import java.math.BigDecimal;
        <#break>
    </#if>
</#list>
<#-------------  END 检测是否存在小数点类型  ---------->
import java.util.Date;
<#if entityLombokModel>
import lombok.*;
</#if>

/**
 * ${table.comment!} 查询请求对象
 * @author ${author}
 * @date ${date}
 * @version ${version}
 */

<#if entityLombokModel>
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
</#if>
<#if swagger2>
@ApiModel(value=" 查询${table.comment!}请求对象")
</#if>
public class ${table.dtoQueryName} extends CreditReportBasePageRequest implements Serializable {

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>

<#-- ----------  加默认主键字段  -------------->
    /** ID */
    @ApiModelProperty(value = "ID", example = "0")
    private Long id;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.propertyName != "orgId" && field.propertyName != "tenantId">
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
    <#assign fieldComment="${field.comment}"/>
    <#if field.comment!?length gt 0>
        <#if field.comment?contains("|")>
            <#assign fieldComment=field.comment?substring(0,field.comment?index_of("|")-1)/>
        </#if>
    </#if>

    <#if field.comment!?length gt 0>
        <#if swagger2>
            <#if field.columnType == "INTEGER" || field.columnType == "LONG">
    @ApiModelProperty(value = "${fieldComment}", example = "0")
            <#elseif field.columnType == "BIG_DECIMAL">
    @ApiModelProperty(value = "${fieldComment}", example = "0.0")
            <#else>
    @ApiModelProperty(value = "${fieldComment}")
            </#if>
        <#else>
    /**
     * ${fieldComment}
     */
        </#if>
    </#if>
    private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>

    @ApiModelProperty(value = "创建开始时间")
    private Date queryStartDate;

    @ApiModelProperty(value = "创建结束时间")
    private Date queryEndDate;
<#------------  END 字段循环遍历  ---------->

<#if !entityLombokModel>
    <#list table.fields as field>
        <#if field.propertyName != "orgId" && field.propertyName != "tenantId">
        <#if field.propertyType == "boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>
    public ${field.propertyType} ${getprefix}${field.capitalName}() {
        return ${field.propertyName};
    }

    <#if chainModel>
    public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    <#else>
    public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    </#if>
        this.${field.propertyName} = ${field.propertyName};
        <#if chainModel>
        return this;
        </#if>
    }
    </#if>
    </#list>
</#if>

<#if !entityLombokModel>
    @Override
    public String toString() {
        return "${entity}{" +
    <#list table.fields as field>
        <#if field_index==0>
            "${field.propertyName}=" + ${field.propertyName} +
        <#else>
            ", ${field.propertyName}=" + ${field.propertyName} +
        </#if>
    </#list>
        "}";
    }
</#if>
}
