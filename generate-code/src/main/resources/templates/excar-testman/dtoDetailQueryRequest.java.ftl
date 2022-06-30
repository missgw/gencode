package ${package.DeleteRequest};

import io.geekidea.springbootplus.framework.core.pagination.BasePageOrderParam;
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.*;
</#if>
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ${table.comment!} 详情请求对象
 *
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
@ApiModel(value="${table.comment!} 详情请求对象")
</#if>
public class ${table.dtoDetailRequestName} extends BasePageOrderParam implements Serializable {

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>

    @ApiModelProperty(value = "${table.comment!}编号", example = "0", required = true)
    private Long ${table.entityName?uncap_first}Id;

<#if !entityLombokModel>
    public Long get${table.entityName}Id() {
        return ${table.entityName?uncap_first}Id;
    }

    public void set${table.entityName}Id(Long ${table.entityName?uncap_first}Id) {
        this.${table.entityName?uncap_first}Id = ${table.entityName?uncap_first}Id;
    }
</#if>

<#if !entityLombokModel>
    @Override
    public String toString() {
        return "${table.dtoDetailRequestName}{" +
            "${table.entityName?uncap_first}Id=" + ${table.entityName?uncap_first}Id +
            '}';
    }
</#if>
}
