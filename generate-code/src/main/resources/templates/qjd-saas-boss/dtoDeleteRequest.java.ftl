package ${package.DeleteRequest};

import com.qjdchina.saas.common.web.request.BossBaseRequest;
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
</#if>
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ${table.comment!} 删除请求对象
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
</#if>
<#if swagger2>
@ApiModel(value="${table.comment!} 删除请求对象")
</#if>
public class ${table.dtoDeleteRequestName} extends BossBaseRequest implements Serializable {

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>

    @ApiModelProperty(value = "${table.comment!}编号", example = "0", required = true)
    @NotNull(message = "${table.comment!}编号不能为空")
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
        return "${table.dtoDeleteRequestName}{" +
            "${table.entityName?uncap_first}Id=" + ${table.entityName?uncap_first}Id +
            '}';
    }
</#if>
}
