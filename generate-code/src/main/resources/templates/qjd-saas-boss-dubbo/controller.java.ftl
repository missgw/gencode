package ${package.Controller};

import com.qjdchina.common.web.security.RequestContext;
import ${package.AddRequest}.${table.dtoAddName};
import ${package.AddRequest}.${table.dtoQueryName};
import ${package.AddRequest}.${table.dtoDetailRequestName};
import ${package.AddRequest}.${table.dtoUpdateName};
import ${package.AddRequest}.${table.dtoDeleteRequestName};
import ${package.DetailResponse}.${table.dtoDetailResponseName};
import ${package.Service}.${table.serviceName};
import MyPageInfo;
import RestBody;
import com.qjdchina.saas.common.auth.Authorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

<#assign tableA=table.xmlName?lower_case>

/**
 * ${table.comment!} 控制器
 *
 * @author ${author}
 * @date ${date}
 * @version ${version}
 */
@Api(tags = "${table.comment!}")
@RestController
@RequestMapping("${controllerRootMapPath}${entity?lower_case}")
public class ${table.controllerName} {

    @Reference
    ${table.serviceName} ${table.serviceName?uncap_first};

    /**
    * 创建${table.comment!}
    * @param ${table.dtoAddName?uncap_first} ${table.comment!}新增请求封装类
    * @return ${table.comment!}基本信息
    */
    @ApiOperation("创建${table.comment!}")
    @PostMapping(path = "/create")
    @Authorize({})
    public RestBody<${table.dtoUpdateName}> create(
        @RequestBody @Validated ${entity}AddRequest ${table.dtoAddName?uncap_first}) {
        ${table.dtoAddName?uncap_first}.setOrgId(RequestContext.getOrgId());
        ${table.dtoUpdateName} ${table.dtoUpdateName?uncap_first} = ${table.serviceName?uncap_first}.create(${table.dtoAddName?uncap_first});
        return RestBody.success(${table.dtoUpdateName?uncap_first});
    }

    /**
    * 查询${table.comment!}列表
    * @param ${table.dtoQueryName?uncap_first} ${table.comment!}查询请求封装类
    * @return ${table.comment!}列表信息，带分页信息
    */
    @ApiOperation("${table.comment!}列表")
    @GetMapping(path = "/list")
    @Authorize({})
    public RestBody<MyPageInfo<${table.dtoDetailResponseName}>> list(
        ${entity}QueryRequest ${table.dtoQueryName?uncap_first}) {
        ${table.dtoQueryName?uncap_first}.setOrgId(RequestContext.getOrgId());
        MyPageInfo<${table.dtoDetailResponseName}> list = ${table.serviceName?uncap_first}.list(${table.dtoQueryName?uncap_first});
        return RestBody.success(list);
    }

    /**
    * 根据id获取对应的${table.comment!}记录信息
    *
    * @param ${table.dtoDetailRequestName?uncap_first} ${table.comment!}详情查询请求类
    * @return ${table.comment!}详情
    */
    @ApiOperation("获取${table.comment!}详情")
    @GetMapping(path = "/get")
    @Authorize({})
    public RestBody<${table.dtoDetailResponseName}> get(@Validated ${table.dtoDetailRequestName} ${table.dtoDetailRequestName?uncap_first}) {
        ${table.dtoDetailRequestName?uncap_first}.setOrgId(RequestContext.getOrgId());
        ${table.dtoDetailResponseName} detail = ${table.serviceName?uncap_first}.detail(${table.dtoDetailRequestName?uncap_first});
        return RestBody.success(detail);
    }

    /**
    * 更新(注意不能所有字段更新)
    *
    * @param ${table.dtoUpdateName?uncap_first} ${table.comment!}更新请求封装类
    * @return ${table.comment!}主键编号
    */
    @ApiOperation("更新${table.comment!}")
    @PostMapping(path = "/update")
    @Authorize({})
    public RestBody<Void> update(
        @RequestBody @Validated ${table.dtoUpdateName} ${table.dtoUpdateName?uncap_first}) {
        ${table.dtoUpdateName?uncap_first}.setOrgId(RequestContext.getOrgId());
        ${table.serviceName?uncap_first}.update${entity}(${table.dtoUpdateName?uncap_first});
        return RestBody.success();
    }

    /**
    * 根据主键删除对应的记录
    *
    * @param i${table.dtoDeleteRequestName?uncap_first} ${table.comment!}删除请求类
    * @return 基本应答信息
    */
    @ApiOperation("删除${table.comment!}-逻辑删除")
    @PostMapping(path = "/delete")
    @Authorize({})
    public RestBody<Void> delete(@RequestBody @Validated ${table.dtoDeleteRequestName} ${table.dtoDeleteRequestName?uncap_first}) {
        ${table.dtoDeleteRequestName?uncap_first}.setOrgId(RequestContext.getOrgId());
        ${table.serviceName?uncap_first}.delete(${table.dtoDeleteRequestName?uncap_first});
        return RestBody.success();
    }
}
