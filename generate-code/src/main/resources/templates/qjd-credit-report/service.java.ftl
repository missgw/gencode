package ${package.Service};

import ${package.AddRequest}.${table.dtoAddName};
import ${package.AddRequest}.${table.dtoQueryName};
import ${package.AddRequest}.${table.dtoDetailRequestName};
import ${package.AddRequest}.${table.dtoUpdateName};
import ${package.AddRequest}.${table.dtoDeleteRequestName};
import ${package.DetailResponse}.${table.dtoDetailResponseName};
import ${package.Entity}.${entity};
import com.qjdchina.creditreport.common.PageInfo;
import com.qjdchina.creditreport.common.web.CreditReportUpdateBaseRequest;
import java.util.List;

/**
 * ${table.comment!}业务处理接口类
 *
 * @author ${author}
 * @date ${date}
 * @version ${version}
 */
public interface ${table.serviceName} extends CreditReportBaseService<${entity}>{

     /**
      * 创建${table.comment!}
      *
      * @param param ${table.comment!}新增对象
      * @return ${table.comment!}更新对象
      */
     ${table.dtoUpdateName} create(${table.dtoAddName} param);

    /**
     * 更新${table.comment!}信息
     *
     * @param param ${table.comment!}更新对象
     * @return 更新后的数据信息
     */
    ${table.dtoUpdateName} update(CreditReportUpdateBaseRequest param);

    /**
     * 保存${table.comment!}信息
     * @param param 保存${table.comment!}入参信息
     * @return 保存到${table.comment!}的最新数据
     */
    ${table.dtoUpdateName} save(CreditReportUpdateBaseRequest param);

     /**
      * 获取${table.comment!}分页列表信息
      *
      * @param param ${table.comment!}查询对象
      * @return ${table.comment!}分页结果集
      */
     PageInfo<${table.dtoDetailResponseName}> page(${table.dtoQueryName} param);

     /**
      * 获取${table.comment!}列表信息
      *
      * @param param ${table.comment!}查询对象
      * @return ${table.comment!}结果集
      */
     List<${table.dtoDetailResponseName}> list(${table.dtoQueryName} param);

<#list table.fields as field>
    <#if field.hasIndex && field.columnType == "STRING">
        <#assign fieldComment=field.comment/>
        <#if field.comment!?length gt 0>
            <#if field.comment?contains(" ")>
                <#assign fieldComment=field.comment?substring(0,field.comment?index_of(" ")-1)/>
            </#if>
        </#if>
    /**
     * 根据${fieldComment}查询${table.comment!}结果集
     *
     * @param ${field.propertyName}s ${field.comment}
     * @return ${table.comment!}结果集
     */
    List<${entity}> getInfoBy${field.propertyName?cap_first}s(Collection<String> ${field.propertyName}s);

    </#if>
</#list>

     /**
      * 获取${table.comment!}列表个数
      *
      * @param param ${table.comment!}查询对象
      * @return ${table.comment!}结果集个数
      */
     int count(${table.dtoQueryName} param);

     /**
      * 获取${table.comment!}详细信息
      *
      * @param param ${table.comment!}详情请求对象
      * @return ${table.comment!}详情信息
      */
     ${table.dtoDetailResponseName} detail(${table.dtoDetailRequestName} param);

     /**
      * 删除${table.comment!}信息
      *
      * @param param ${table.comment!}删除请求对象
      * @return ${table.comment!}详情
      */
     int delete(${table.dtoDeleteRequestName} param);

}
