package ${package.Controller};

import ${package.AddRequest}.${table.dtoAddName};
import ${package.AddRequest}.${table.dtoQueryName};
import ${package.AddRequest}.${table.dtoDetailRequestName};
import ${package.AddRequest}.${table.dtoUpdateName};
import ${package.AddRequest}.${table.dtoDeleteRequestName};
import ${package.DetailResponse}.${table.dtoDetailResponseName};
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import com.qjdchina.saas.common.web.MyPageInfo;
import com.qjdchina.saas.common.web.RestBody;
import com.qjdchina.saas.common.auth.Authorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
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
        ${table.dtoUpdateName} ${table.dtoUpdateName?uncap_first} = ${table.serviceName?uncap_first}.create(${table.dtoAddName?uncap_first});
        return RestBody.success(${table.dtoUpdateName?uncap_first});
    }

    /**
    * 查询${table.comment!}列表
    * @param ${table.dtoQueryName?uncap_first} ${table.comment!}查询请求封装类
    * @param page 第几页
    * @param size 每页显示条数
    * @return ${table.comment!}列表信息，带分页信息
    */
    @ApiOperation("${table.comment!}列表")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "第几页"),
    @ApiImplicitParam(name = "size", value = "每页条数")
    })
    @GetMapping(path = "/list/{page}/{size}")
    @Authorize({})
    public RestBody<MyPageInfo<${table.dtoDetailResponseName}>> list(
        ${entity}QueryRequest ${table.dtoQueryName?uncap_first}, @PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page, size);
        MyPageInfo<${table.dtoDetailResponseName}> list = ${table.serviceName?uncap_first}.list(${table.dtoQueryName?uncap_first}, pageable);
            return RestBody.success(list);
    }

    /**
    * 根据id获取对应的${table.comment!}记录信息
    *
    * @param id ${table.comment!}主键编号
    * @return ${table.comment!}详情
    */
    @ApiOperation("获取${table.comment!}详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "${table.comment!}编号")})
    @GetMapping(path = "/get/{id}")
    @Authorize({})
    public RestBody<${table.dtoDetailResponseName}> get(@PathVariable Long id) {
        ${table.dtoDetailRequestName} ${table.dtoDetailRequestName?uncap_first} = new ${table.dtoDetailRequestName}();
        ${table.dtoDetailRequestName?uncap_first}.set${entity}Id(id);

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
        ${table.serviceName?uncap_first}.update${entity}(${table.dtoUpdateName?uncap_first});
        return RestBody.success();
    }

    /**
    * 根据主键删除对应的记录
    *
    * @param id ${table.comment!}主键编号
    * @return 基本应答信息
    */
    @ApiOperation("删除${table.comment!}-逻辑删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键编号")})
    @PostMapping(path = "/delete/{id}")
    @Authorize({})
    public RestBody<Void> delete(@PathVariable Long id) {
        ${table.dtoDeleteRequestName} ${table.dtoDeleteRequestName?uncap_first} = new ${table.dtoDeleteRequestName}();
        ${table.dtoDeleteRequestName?uncap_first}.set${entity}Id(id);
        ${table.serviceName?uncap_first}.delete(${table.dtoDeleteRequestName?uncap_first});
        return RestBody.success();
    }
}
