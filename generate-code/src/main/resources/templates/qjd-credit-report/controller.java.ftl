package ${package.Controller};

import com.qjdchina.creditreport.common.RestBody;
import ${package.AddRequest}.${table.dtoAddName};
import ${package.AddRequest}.${table.dtoQueryName};
import ${package.AddRequest}.${table.dtoDetailRequestName};
import ${package.AddRequest}.${table.dtoUpdateName};
import ${package.AddRequest}.${table.dtoDeleteRequestName};
import ${package.DetailResponse}.${table.dtoDetailResponseName};
import ${package.Service}.${table.serviceName};
import com.qjdchina.creditreport.common.PageInfo;
import com.qjdchina.creditreport.common.validate.ValidateId;
import com.qjdchina.creditreport.utils.UserAdaptor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

<#assign tableA=table.xmlName?lower_case>

/**
 * ${table.comment!}控制器
 *
 * @author ${author}
 * @date ${date}
 * @version ${version}
 */

@Api(tags = "${table.comment!}")
@RestController
<#if entity?lower_case=="personcreditreportbase">
@RequestMapping("/${entity?lower_case}")
<#else>
@RequestMapping("/${entity?lower_case?replace("creditreport","")}")
</#if>
public class ${table.controllerName} {

    @Resource
    ${table.serviceName} ${table.serviceName?uncap_first};
    @Resource
    UserAdaptor userAdaptor;

    /**
     * 创建${table.comment!}
     * @param param ${table.comment!}新增请求封装类
     * @return ${table.comment!}基本信息
     */
    @ApiOperation("创建${table.comment!}")
    @PostMapping(path = "/create")
    public RestBody<${table.dtoUpdateName}> create(
        @RequestBody @Validated ${entity}AddRequest param) {
        param.setLoginUserId(userAdaptor.getLdapUserId());
        return RestBody.success(${table.serviceName?uncap_first}.create(param));
    }

    /**
     * 更新(注意不能所有字段更新)
     *
     * @param param ${table.comment!}更新请求封装类
     * @return ${table.comment!}主键编号
     */
    @ApiOperation("更新${table.comment!}")
    @PostMapping(path = "/update")
    public RestBody<Void> update(@RequestBody @Validated(ValidateId.class) ${table.dtoUpdateName} param) {
        param.setLoginUserId(userAdaptor.getLdapUserId());
        ${table.serviceName?uncap_first}.update(param);
        return RestBody.success();
    }

    /**
    * 保存（新增或更新）
    *
    * @param param ${table.comment!}保存请求封装类
    * @return ${table.comment!}基本信息
    */
    @ApiOperation("保存${table.comment!}信息")
    @PostMapping(path = "/save")
    public RestBody<${table.dtoUpdateName}> save(@RequestBody @Validated ${table.dtoUpdateName} param) {
        param.setLoginUserId(userAdaptor.getLdapUserId());
        return RestBody.success(${table.serviceName?uncap_first}.save(param));
    }

    /**
     * 查询${table.comment!}分页列表
     * @param param ${table.comment!}查询请求封装类
     * @return ${table.comment!}列表信息，带分页信息
     */
    @ApiOperation("${table.comment!}分页列表")
    @GetMapping(path = "/page")
    public RestBody<PageInfo<${table.dtoDetailResponseName}>> page(${entity}QueryRequest param) {
        param.setLoginUserId(userAdaptor.getLdapUserId());
        PageInfo<${table.dtoDetailResponseName}> page = ${table.serviceName?uncap_first}.page(param);
        return RestBody.success(page);
    }

    /**
     * 查询${table.comment!}列表
     * @param param ${table.comment!}查询请求封装类
     * @return ${table.comment!}列表信息
     */
    @ApiOperation("${table.comment!}列表")
    @GetMapping(path = "/list")
    public RestBody<List<${table.dtoDetailResponseName}>> list(${entity}QueryRequest param) {
        param.setLoginUserId(userAdaptor.getLdapUserId());
        List<${table.dtoDetailResponseName}> list = ${table.serviceName?uncap_first}.list(param);
        return RestBody.success(list);
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
        param.setLoginUserId(userAdaptor.getLdapUserId());
        ${table.dtoDetailResponseName} detail = ${table.serviceName?uncap_first}.detail(param);
        return RestBody.success(detail);
    }

    /**
     * 根据主键删除对应的记录
     *
     * @param param ${table.comment!}删除请求类
     * @return 基本应答信息
     */
    @ApiOperation("逻辑删除")
    @PostMapping(path = "/delete")
    public RestBody<Void> delete(@RequestBody @Validated ${table.dtoDeleteRequestName} param) {
        param.setLoginUserId(userAdaptor.getLdapUserId());
        ${table.serviceName?uncap_first}.delete(param);
        return RestBody.success();
    }
}
