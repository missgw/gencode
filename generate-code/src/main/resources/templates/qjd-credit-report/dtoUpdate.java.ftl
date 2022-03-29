package ${package.AddRequest};

import com.qjdchina.creditreport.common.web.CreditReportUpdateBaseRequest;
import com.qjdchina.creditreport.common.validate.ValidateField;
import com.qjdchina.creditreport.common.validate.ValidateRemark;
import com.qjdchina.creditreport.common.validate.ValidateId;
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
<#assign importClassList=validateParser.getImportClassList(table.fields)>
<#list importClassList as im>
${im}
</#list>

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
@ApiModel(value="更新${table.comment!}请求对象")
</#if>
public class ${table.dtoUpdateName} extends CreditReportUpdateBaseRequest implements Serializable {

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#assign externalAnno=validateParser.getFieldValidatorAnnotation(field)>
    <#if field.propertyName != "orgId" && field.propertyName != "tenantId">
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
    <#assign fieldComment=validateParser.removeUnit(field.comment)/>
    <#if field.comment!?length gt 0>
        <#if swagger2>

    @ApiModelProperty(value = "${field.comment}", required = true)
        <#else>
    /**
     * ${field.comment}
     */
     </#if>
    </#if>
    <#if field.columnType== "STRING" && field.propertyName=="remark">
    @NotBlank(message = "${fieldComment}不能为空", groups = ValidateRemark.class)
    <#elseif field.columnType== "STRING">
    @NotBlank(message = "${fieldComment}不能为空", groups = ValidateField.class)
    <#elseif field.columnType== "LONG">
    @NotNull(message = "${fieldComment}不能为空", groups = ValidateField.class)
    <#elseif field.columnType== "INT">
    @NotNull(message = "${fieldComment}不能为空", groups = ValidateField.class)
    <#elseif field.columnType== "DATE">
    @NotNull(message = "${fieldComment}不能为空", groups = ValidateField.class)
    <#else>
    @NotNull(message = "${fieldComment}不能为空", groups = ValidateField.class)
    </#if>
    <#if externalAnno!="">
    ${externalAnno}
    private ${field.propertyType} ${field.propertyName};
    <#else>
    private ${field.propertyType} ${field.propertyName};
    </#if>
    </#if>
</#list>
<#------------  END 字段循环遍历  ---------->

<#if !entityLombokModel>
    <#list table.fields as field>
        <#if field.propertyName != "orgId">
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