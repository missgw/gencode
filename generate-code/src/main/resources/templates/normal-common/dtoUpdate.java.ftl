package ${package.AddRequest};

<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
import java.io.Serializable;
<#if entityLombokModel>
import lombok.*;
</#if>
<#-- ----------  BEGIN 检测是否存在字符串类型  ---------->
<#list table.fields as field>
    <#if field.columnType== "STRING">
import javax.validation.constraints.NotBlank;
        <#break>
    </#if>
</#list>
<#-------------  END 检测是否存在字符串类型  ---------->
import javax.validation.constraints.NotNull;
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

/**
 * ${table.comment!} 更新请求对象
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
@ApiModel(value="${entity} 更新请求对象", description="${table.comment!} 更新请求实体对象")
</#if>
public class ${table.dtoUpdateName} implements Serializable {

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
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
    @ApiModelProperty(value = "${field.comment}", required = true)
        <#else>
    /**
     * ${field.comment}
     */
     </#if>
    </#if>
    <#if field.columnType== "STRING">
    @NotBlank(message = "${fieldComment}不能为空")
    <#elseif field.columnType== "LONG">
    @NotNull(message = "${fieldComment}不能为空")
    <#elseif field.columnType== "INT">
    @NotNull(message = "${fieldComment}不能为空")
    <#elseif field.columnType== "DATE">
    @NotNull(message = "${fieldComment}不能为空")
    <#else>
    @NotNull(message = "${fieldComment}不能为空")
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

<#if !entityLombokModel>
    <#list table.fields as field>
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
