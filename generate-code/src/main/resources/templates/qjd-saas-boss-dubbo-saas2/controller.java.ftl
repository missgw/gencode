package ${package.Controller};

import ${package.AddRequest}.${table.dtoAddName};
import ${package.AddRequest}.${table.dtoQueryName};
import ${package.AddRequest}.${table.dtoDetailRequestName};
import ${package.AddRequest}.${table.dtoUpdateName};
import ${package.AddRequest}.${table.dtoDeleteRequestName};
import ${package.DetailResponse}.${table.dtoDetailResponseName};
import ${package.Service}.${table.serviceName};
import MyPageInfo;
import RestBody;
import com.qjdchina.saas.core.OrgUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

<#assign tableA=table.xmlName?lower_case>

/**
 * ${table.comment!} Web服务
 *
 * @author ${author}
 * @date ${date}
 * @version ${version}
 */

@Api(tags = "${table.comment!}")
@RestController
@RequestMapping("${controllerRootMapPath}${entity?lower_case}")
public class ${table.controllerName} {

    @Resource
    ${table.serviceName} ${table.serviceName?uncap_first};
    @Resource
    OrgUser orgUser;

    /**
    * 创建${table.comment!}
    * @param param ${table.comment!}新增请求封装类
    * @return ${table.comment!}基本信息
    */
    @ApiOperation("创建${table.comment!}")
    @PostMapping(path = "/create")
    public RestBody<${table.dtoUpdateName}> create(
        @RequestBody @Validated ${entity}AddRequest param) {
        param.setOrgId(orgUser.getOrgId());
        param.setCurrentMemberId(orgUser.getMemberId());
        return RestBody.success(${table.serviceName?uncap_first}.create(param));
    }

    /**
    * 查询${table.comment!}列表
    * @param param ${table.comment!}查询请求封装类
    * @return ${table.comment!}列表信息，带分页信息
    */
    @ApiOperation("${table.comment!}列表")
    @GetMapping(path = "/page")
    public RestBody<MyPageInfo<${table.dtoDetailResponseName}>> page(
        ${entity}QueryRequest param) {
        param.setOrgId(orgUser.getOrgId());
        param.setCurrentMemberId(orgUser.getMemberId());
        MyPageInfo<${table.dtoDetailResponseName}> page = ${table.serviceName?uncap_first}.page(param);
        return RestBody.success(page);
    }

    /**
    * 根据id获取对应的${table.comment!}记录信息
    *
    * @param param ${table.comment!}详情查询请求类
    * @return ${table.comment!}详情
    */
    @ApiOperation("获取${table.comment!}详情")
    @GetMapping(path = "/get")
    public RestBody<${table.dtoDetailResponseName}> get(@Validated ${table.dtoDetailRequestName} param) {
        param.setOrgId(orgUser.getOrgId());
        param.setCurrentMemberId(orgUser.getMemberId());
        ${table.dtoDetailResponseName} detail = ${table.serviceName?uncap_first}.detail(param);
        return RestBody.success(detail);
    }

    /**
    * 更新(注意不能所有字段更新)
    *
    * @param param ${table.comment!}更新请求封装类
    * @return ${table.comment!}主键编号
    */
    @ApiOperation("更新${table.comment!}")
    @PostMapping(path = "/update")
    public RestBody<Void> update(@RequestBody @Validated ${table.dtoUpdateName} param) {
        param.setOrgId(orgUser.getOrgId());
        param.setCurrentMemberId(orgUser.getMemberId());
        ${table.serviceName?uncap_first}.update(param);
        return RestBody.success();
    }

    /**
    * 根据主键删除对应的记录
    *
    * @param param ${table.comment!}删除请求类
    * @return 基本应答信息
    */
    @ApiOperation("删除${table.comment!}-逻辑删除")
    @PostMapping(path = "/delete")
    public RestBody<Void> delete(@RequestBody @Validated ${table.dtoDeleteRequestName} param) {
        param.setOrgId(orgUser.getOrgId());
        param.setCurrentMemberId(orgUser.getMemberId());
        ${table.serviceName?uncap_first}.delete(param);
        return RestBody.success();
    }
}
