package ${package.Service};

import com.baomidou.mybatisplus.extension.service.IService;
import ${package.AddRequest}.${table.dtoAddName};
import ${package.AddRequest}.${table.dtoQueryName};
import ${package.AddRequest}.${table.dtoDetailRequestName};
import ${package.AddRequest}.${table.dtoUpdateName};
import ${package.AddRequest}.${table.dtoDeleteRequestName};
import ${package.DetailResponse}.${table.dtoDetailResponseName};
import ${package.Entity}.${entity};
import com.qjdchina.saas.common.web.MyPageInfo;
<#list table.fields as field>
    <#if field.hasIndex && field.columnType == "STRING">
import java.util.Collection;
import java.util.List;
        <#break>
    </#if>
</#list>

/**
 * ${table.comment!}业务处理接口类
 *
 * @author ${author}
 * @date ${date}
 * @version ${version}
 */
public interface ${table.serviceName} extends IService<${entity}>{

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
      * @return 返回更新条数
      */
     int update${entity}(${table.dtoUpdateName} param);

     /**
      * 获取${table.comment!}列表信息
      *
      * @param param ${table.comment!}查询对象
      * @return ${table.comment!}结果集
      */
     MyPageInfo<${table.dtoDetailResponseName}> page(${table.dtoQueryName} param);

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
     * @param param ${field.comment}
     * @return ${table.comment!}结果集
     */
    List<${entity}> get${entity}By${field.propertyName?cap_first}s(Collection<String> ${field.propertyName}s, String orgId);

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
