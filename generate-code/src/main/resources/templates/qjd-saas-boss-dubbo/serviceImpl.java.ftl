package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import ${superServiceImplClassPackage};
import ${package.Service}.${table.serviceName};
import com.github.pagehelper.page.PageMethod;
import ${package.AddRequest}.${table.dtoAddName};
import ${package.AddRequest}.${table.dtoQueryName};
import ${package.AddRequest}.${table.dtoDetailRequestName};
import ${package.AddRequest}.${table.dtoUpdateName};
import ${package.AddRequest}.${table.dtoDeleteRequestName};
import ${package.DetailResponse}.${table.dtoDetailResponseName};
import ${package.Mapper}.${table.mapperName};
import ${package.Entity}.${entity};
import BaseModel;
import MyPageInfo;
import com.qjdchina.saas.common.web.MyPageUtil;
import com.qjdchina.saas.common.exception.error.BusinessErrorCode;
import com.qjdchina.saas.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

     @Resource
     ModelMapper modelMapper;
     @Resource
     ${table.mapperName} ${table.mapperName?uncap_first};

     /**
      * 创建${table.comment!}
      *
      * @param ${table.dtoAddName?uncap_first} ${table.comment!}新增对象
      * @return ${table.comment!}更新对象
      */
     @Override
     @Transactional(rollbackFor = Exception.class)
     public ${table.dtoUpdateName} create(${table.dtoAddName} ${table.dtoAddName?uncap_first}) {
       ${entity} ${entity?uncap_first} = convertToModel(${table.dtoAddName?uncap_first});

       ${table.mapperName?uncap_first}.insert(${entity?uncap_first});
       log.info("创建${table.comment!},{}", ${entity?uncap_first});

       return convertToDto(${entity?uncap_first});
     }

     /**
      * 更新${table.comment!}信息
      *
      * @param ${table.dtoUpdateName?uncap_first} ${table.comment!}更新对象
      * @return 返回更新条数
      */
     @Override
     @Transactional(rollbackFor = Exception.class)
     public int update${entity}(${table.dtoUpdateName} ${table.dtoUpdateName?uncap_first}) {
       // 先取回之前数据
       ${entity} ${entity?uncap_first} = getById(${table.dtoUpdateName?uncap_first}.getId());

       // 如果不存在，需要报异常
       if (${entity?uncap_first} == null) {
        throw new BusinessException(BusinessErrorCode.RECORD_NOT_FOUND);
       }

     <#list table.fields as field>
        <#if field.propertyName != "orgId">
       ${entity?uncap_first}.set${field.propertyName?cap_first}(${table.dtoUpdateName?uncap_first}.get${field.propertyName?cap_first}());
        </#if>
     </#list>

       return ${table.mapperName?uncap_first}.updateById(${entity?uncap_first});
     }

     /**
      * 获取${table.comment!}列表信息
      *
      * @param ${table.dtoQueryName?uncap_first} ${table.comment!}查询对象
      * @param pageable 分页信息
      * @return ${table.comment!}结果集
      */
     @Override
     public MyPageInfo<${table.dtoDetailResponseName}> list(${table.dtoQueryName} ${table.dtoQueryName?uncap_first}, Pageable pageable) {
        if (pageable != null) {
            PageMethod.startPage(pageable.getPageNumber(), pageable.getPageSize());
        }
        LambdaQueryWrapper<${entity}> mapper = queryWrapper(${table.dtoQueryName?uncap_first});
        mapper.orderByDesc(BaseModel::getCreatedTime);

        MyPageInfo<${entity}> myPageInfo = new MyPageInfo<>(${table.mapperName?uncap_first}.selectList(mapper));
        return MyPageUtil.convertTo(myPageInfo, ${table.dtoDetailResponseName}.class);
     }

    /**
     * 获取${table.comment!}列表个数
     *
     * @param ${table.dtoQueryName?uncap_first} ${table.comment!}查询对象
     * @return ${table.comment!}结果集个数
     */
    @Override
    public int count(${table.dtoQueryName} ${table.dtoQueryName?uncap_first}) {
        return ${table.mapperName?uncap_first}.selectCount(queryWrapper(${table.dtoQueryName?uncap_first}));
    }

    /**
     * 获取${table.comment!}详细信息
     *
     * @param ${table.dtoDetailRequestName?uncap_first} ${table.comment!}详情查询请求对象
     * @return ${table.comment!}详情信息
     */
    @Override
    public ${table.dtoDetailResponseName} detail(${table.dtoDetailRequestName} ${table.dtoDetailRequestName?uncap_first}) {
        ${entity} ${entity?uncap_first} = ${entity?uncap_first}Mapper.selectById(${table.dtoDetailRequestName?uncap_first}.get${entity}Id());
        return convertToDetail(${entity?uncap_first});
    }

    /**
     * 删除${table.comment!}信息
     *
     * @param ${table.dtoDeleteRequestName?uncap_first} ${table.comment!}删除请求对象
     * @return ${table.comment!}详情
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(${table.dtoDeleteRequestName} ${table.dtoDeleteRequestName?uncap_first}) {
        return ${entity?uncap_first}Mapper.deleteById(${table.dtoDeleteRequestName?uncap_first}.get${entity}Id());
    }

    private LambdaQueryWrapper<${entity}> queryWrapper(${table.dtoQueryName} ${table.dtoQueryName?uncap_first}) {
        LambdaQueryWrapper<${entity}> mapper = new LambdaQueryWrapper<>(convertToModel(${table.dtoQueryName?uncap_first}));
        mapper.ge(${table.dtoQueryName?uncap_first}.getQueryStartDate() != null ,BaseModel::getCreatedTime,${table.dtoQueryName?uncap_first}.getQueryStartDate());
        mapper.le(${table.dtoQueryName?uncap_first}.getQueryEndDate()!= null ,BaseModel::getCreatedTime,${table.dtoQueryName?uncap_first}.getQueryEndDate());
        return mapper;
    }

     private ${table.dtoUpdateName} convertToDto(${entity} ${entity?uncap_first}) {
        return modelMapper.map(${entity?uncap_first}, ${table.dtoUpdateName}.class);
     }

     private ${table.dtoDetailResponseName} convertToDetail(${entity} ${entity?uncap_first}) {
        return modelMapper.map(${entity?uncap_first}, ${table.dtoDetailResponseName}.class);
     }

     private ${entity} convertToModel(${table.dtoAddName} ${table.dtoAddName?uncap_first}) {
        return modelMapper.map(${table.dtoAddName?uncap_first}, ${entity}.class);
     }

     private ${entity} convertToModel(${table.dtoQueryName} ${table.dtoQueryName?uncap_first}) {
        return modelMapper.map(${table.dtoQueryName?uncap_first}, ${entity}.class);
     }
}
