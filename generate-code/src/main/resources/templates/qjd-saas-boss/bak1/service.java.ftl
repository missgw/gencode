package ${package.Service};

import com.baomidou.mybatisplus.extension.service.IService;
import ${package.AddRequest}.${table.dtoAddName};
import ${package.AddRequest}.${table.dtoQueryName};
import ${package.AddRequest}.${table.dtoDetailRequestName};
import ${package.AddRequest}.${table.dtoUpdateName};
import ${package.AddRequest}.${table.dtoDeleteRequestName};
import ${package.DetailResponse}.${table.dtoDetailResponseName};
import ${package.Entity}.${entity};
import MyPageInfo;
import org.springframework.data.domain.Pageable;

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
      * @param ${table.dtoAddName?uncap_first} ${table.comment!}新增对象
      * @return ${table.comment!}更新对象
      */
     ${table.dtoUpdateName} create(${table.dtoAddName} ${table.dtoAddName?uncap_first});

     /**
      * 更新${table.comment!}信息
      *
      * @param ${table.dtoUpdateName?uncap_first} ${table.comment!}更新对象
      * @return 返回更新条数
      */
     int update${entity}(${table.dtoUpdateName} ${table.dtoUpdateName?uncap_first});

     /**
      * 获取${table.comment!}列表信息
      *
      * @param ${table.dtoQueryName?uncap_first} ${table.comment!}查询对象
      * @param pageable 分页信息
      * @return ${table.comment!}结果集
      */
     MyPageInfo<${entity}> list(${table.dtoQueryName} ${table.dtoQueryName?uncap_first}, Pageable pageable);

     /**
      * 获取${table.comment!}列表个数
      *
      * @param ${table.dtoQueryName?uncap_first} ${table.comment!}查询对象
      * @return ${table.comment!}结果集个数
      */
     int count(${table.dtoQueryName} ${table.dtoQueryName?uncap_first});

     /**
      * 获取${table.comment!}详细信息
      *
      * @param ${table.dtoDetailRequestName?uncap_first} ${table.comment!}详情请求对象
      * @return ${table.comment!}详情信息
      */
     ${table.dtoDetailResponseName} detail(${table.dtoDetailRequestName} ${table.dtoDetailRequestName?uncap_first});

     /**
      * 删除${table.comment!}信息
      *
      * @param ${table.dtoDeleteRequestName?uncap_first} ${table.comment!}删除请求对象
      * @return ${table.comment!}详情
      */
     int delete(${table.dtoDeleteRequestName} ${table.dtoDeleteRequestName?uncap_first});

}
