package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import ${superServiceImplClassPackage};
import ${package.Service}.${table.serviceName};
import ${package.AddRequest}.${table.dtoAddName};
import ${package.AddRequest}.${table.dtoQueryName};
import ${package.AddRequest}.${table.dtoDetailRequestName};
import ${package.AddRequest}.${table.dtoUpdateName};
import ${package.AddRequest}.${table.dtoDeleteRequestName};
import ${package.DetailResponse}.${table.dtoDetailResponseName};
import ${package.Mapper}.${table.mapperName};
import ${package.Entity}.${entity};
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.common.api.ApiCode;
import io.geekidea.springbootplus.framework.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.testman.home.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
<#list table.fields as field>
    <#if field.hasIndex && field.columnType == "STRING">
import java.util.Collection;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import com.qjdchina.saas.${projectName}.util.CheckValueUtil;
        <#break>
    </#if>
</#list>
/**
 * ${table.comment!}业务处理类
 *
 * @author ${author}
 * @date ${date}
 * @version ${version}
 */
@Service
@Slf4j
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

     @Autowired
     private ${table.mapperName} ${table.mapperName?uncap_first};

     /**
      * 创建${table.comment!}
      *
      * @param param ${table.comment!}新增对象
      * @return ${table.comment!}更新对象
      */
     @Override
     @Transactional(rollbackFor = Exception.class)
     public ${table.dtoUpdateName} create(${table.dtoAddName} param) {
       ${entity} ${entity?uncap_first} = ConvertUtils.sourceToTarget(param,${entity}.class);

       ${table.mapperName?uncap_first}.insert(${entity?uncap_first});
       log.info("创建${table.comment!},{}", ${entity?uncap_first});

       return ConvertUtils.sourceToTarget(${entity?uncap_first},${table.dtoUpdateName}.class);
     }

     /**
      * 更新${table.comment!}信息
      *
      * @param param ${table.comment!}更新对象
      * @return 返回更新条数
      */
     @Override
     @Transactional(rollbackFor = Exception.class)
     public int update${entity}(${table.dtoUpdateName} param) {
       // 先取回之前数据
       ${entity} ${entity?uncap_first} = getById(param.getId());

       // 如果不存在，需要报异常
       if (${entity?uncap_first} == null) {
        throw new BusinessException(ApiCode.RECORD_NOT_FOUND);
       }

     <#list table.fields as field>
        <#if field.propertyName != "orgId" && field.propertyName != "tenantId">
       ${entity?uncap_first}.set${field.propertyName?cap_first}(param.get${field.propertyName?cap_first}());
        </#if>
     </#list>

       return ${table.mapperName?uncap_first}.updateById(${entity?uncap_first});
     }

     /**
      * 获取${table.comment!}列表信息
      *
      * @param param ${table.comment!}查询对象
      * @return ${table.comment!}结果集
      */
     @Override
     public Paging<${table.dtoDetailResponseName}> page(${table.dtoQueryName} param) {
        LambdaQueryWrapper<${entity}> mapper = queryWrapper(param);
        Page<${entity}> page = new PageInfo<>(param, OrderItem.desc(getLambdaColumn(${entity}::getCreateTime)));
        IPage<${entity}> iPage = ${table.mapperName?uncap_first}.selectPage(page,mapper);
        return new Paging<>(ConvertUtils.pageVoCovert(iPage,${table.dtoDetailResponseName}.class));
     }

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
    @Override
    public List<${entity}> get${entity}By${field.propertyName?cap_first}s(Collection<String> ${field.propertyName}s, String orgId){
        String errorDesc = "${fieldComment}不能为空";

        if (CollectionUtils.isEmpty(${field.propertyName}s)) {
            throw new BusinessException(errorDesc);
        }

        ${field.propertyName}s.forEach(data -> {
            if (data == null) {
                throw new BusinessException(errorDesc);
            }
        });

        CheckValueUtil.checkOrgId(orgId);

        LambdaQueryWrapper<${entity}> lambdaQuery = Wrappers.<${entity}>lambdaQuery()
            .in(${entity}::get${field.propertyName?cap_first}, ${field.propertyName}s)
            .eq(${entity}::getOrgId, orgId);

        return ${table.mapperName?uncap_first}.selectList(lambdaQuery);
    }

    </#if>
</#list>

    /**
     * 获取${table.comment!}列表个数
     *
     * @param param ${table.comment!}查询对象
     * @return ${table.comment!}结果集个数
     */
    @Override
    public int count(${table.dtoQueryName} param) {
        return ${table.mapperName?uncap_first}.selectCount(queryWrapper(param));
    }

    /**
     * 获取${table.comment!}详细信息
     *
     * @param param ${table.comment!}详情查询请求对象
     * @return ${table.comment!}详情信息
     */
    @Override
    public ${table.dtoDetailResponseName} detail(${table.dtoDetailRequestName} param) {
        ${entity} ${entity?uncap_first} = ${entity?uncap_first}Mapper.selectById(param.get${entity}Id());
        return ConvertUtils.sourceToTarget(${entity?uncap_first},${table.dtoDetailResponseName}.class);
    }

    /**
     * 删除${table.comment!}信息
     *
     * @param param ${table.comment!}删除请求对象
     * @return ${table.comment!}详情
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(${table.dtoDeleteRequestName} param) {
        return ${entity?uncap_first}Mapper.deleteById(param.get${entity}Id());
    }

    private LambdaQueryWrapper<${entity}> queryWrapper(${table.dtoQueryName} param) {
        LambdaQueryWrapper<${entity}> mapper = Wrappers.lambdaQuery(ConvertUtils.sourceToTarget(param,${entity}.class));
<#--        mapper.ge(param.getQueryStartDate() != null ,BaseModel::getCreatedTime,param.getQueryStartDate());-->
<#--        mapper.le(param.getQueryEndDate()!= null ,BaseModel::getCreatedTime,param.getQueryEndDate());-->
        return mapper;
    }

}