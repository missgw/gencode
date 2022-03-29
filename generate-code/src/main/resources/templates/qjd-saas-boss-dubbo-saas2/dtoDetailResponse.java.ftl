package ${package.DetailResponse};

<#list table.importPackages as pkg>
    <#if pkg != "com.baomidou.mybatisplus.annotation.TableName"
    && pkg != "com.baomidou.mybatisplus.annotation.TableField">
import com.qjdchina.saas.common.web.vo.BossBaseModelV2;
        <#break/>
    </#if>
</#list>
<#-- ----------  BEGIN 检测是否存在日期类型  ---------->
<#list table.fields as field>
    <#if field.columnType== "DATE">
import java.util.Date;
        <#break>
    </#if>
</#list>
<#-------------  END 检测是否存在日期类型  ---------->
<#-- ----------  BEGIN 检测是否存在小数点类型  ---------->
<#list table.fields as field>
    <#if field.columnType== "BIG_DECIMAL">
import java.math.BigDecimal;
        <#break>
    </#if>
</#list>
<#-------------  END 检测是否存在小数点类型  ---------->
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.*;
</#if>

/**
 * ${table.comment!} 详情返回对象
 * @author ${author}
 * @date ${date}
 * @version ${version}
 */

<#if entityLombokModel>
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
</#if>
<#if swagger2>
@ApiModel(value="${table.comment!} 详情返回实体对象")
</#if>
<#if superEntityClass??>
public class ${table.dtoDetailResponseName} extends ${superEntityClass} {
<#else>
public class ${table.dtoDetailResponseName} implements Serializable {
</#if>

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.propertyName != "orgId" && field.propertyName != "tenantId">
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
        <#if swagger2>
            <#if field.columnType == "INTEGER" || field.columnType == "LONG">
    @ApiModelProperty(value = "${field.comment}")
            <#elseif field.columnType == "BIG_DECIMAL">
    @ApiModelProperty(value = "${field.comment}")
            <#else>
    @ApiModelProperty(value = "${field.comment}")
            </#if>
        <#else>
    /**
     * ${field.comment}
     */
        </#if>
    </#if>
    private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>
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
