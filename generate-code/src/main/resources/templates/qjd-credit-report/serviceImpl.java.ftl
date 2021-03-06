package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
import com.qjdchina.commons.exception.BusinessException;
import com.qjdchina.creditreport.dto.response.BatchResultInfo;
import com.qjdchina.creditreport.common.BusinessErrorCode;
import com.qjdchina.creditreport.common.PageInfo;
import com.qjdchina.creditreport.common.PageUtil;
import com.qjdchina.creditreport.common.model.CreditReportBaseModel;
import com.qjdchina.creditreport.common.utils.CreditReportUtil;
import com.qjdchina.creditreport.common.web.CreditReportUpdateBaseRequest;
import com.qjdchina.creditreport.utils.CheckValueUtil;
import com.qjdchina.creditreport.utils.CreditReportAssist;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import javax.annotation.Resource;
<#list table.fields as field>
    <#if field.hasIndex && field.columnType == "STRING">
import org.apache.commons.collections.CollectionUtils;
        <#break>
    </#if>
</#list>

import static com.qjdchina.creditreport.common.DataConverter.convertObject;
import static com.qjdchina.creditreport.common.DataConverter.convertList;
import static java.util.stream.Collectors.toList;

/**
 * ${table.comment!}???????????????
 *
 * @author ${author}
 * @date ${date}
 * @version ${version}
 */
@Service
@Slf4j
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

     @Resource
     ${table.mapperName} ${table.mapperName?uncap_first};

     /**
      * ??????${table.comment!}
      *
      * @param param ${table.comment!}????????????
      * @return ${table.comment!}????????????
      */
     @Override
     @Transactional(rollbackFor = Exception.class)
     public ${table.dtoUpdateName} create(${table.dtoAddName} param) {
       ${entity} ${entity?uncap_first} = convertObject(param,${entity}.class);

       ${entity?uncap_first}.setCreator(StringUtils.defaultIfEmpty(String.valueOf(param.getLoginUserId()),""));
       ${table.mapperName?uncap_first}.insert(${entity?uncap_first});
       log.info("??????${table.comment!},{}", ${entity?uncap_first});

       return convertObject(${entity?uncap_first},${table.dtoUpdateName}.class);
     }

     /**
      * ??????${table.comment!}??????
      *
      * @param param ${table.comment!}????????????
      * @return ????????????????????????
      */
     @Override
     @Transactional(rollbackFor = Exception.class)
     public ${table.dtoUpdateName} update(CreditReportUpdateBaseRequest param) {
        // ?????????????????????
        ${entity} ${entity?uncap_first} = getById(param.getId());

        // ?????????????????????????????????
        if (${entity?uncap_first} == null) {
            throw new BusinessException(BusinessErrorCode.RECORD_NOT_FOUND);
        }

        BeanUtils.copyProperties(param, ${entity?uncap_first});
        ${entity?uncap_first}.setModifier(StringUtils.defaultIfEmpty(String.valueOf(param.getLoginUserId()), ""));

        ${table.mapperName?uncap_first}.updateById(${entity?uncap_first});
        return convertObject(${entity?uncap_first},${table.dtoUpdateName}.class);
    }

    /**
     * ??????${table.comment!}??????
     * @param param ??????${table.comment!}????????????
     * @return ?????????${table.comment!}???????????????
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${table.dtoUpdateName} save(CreditReportUpdateBaseRequest param) {
        if (CheckValueUtil.isNullOrZero(param.getId())) {
            return create(convertObject(param,${table.dtoAddName}.class));
        } else {
            return update(param);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSave(List<? extends CreditReportUpdateBaseRequest> param) {
        BatchResultInfo info = CreditReportAssist.wrapResult(param);

        // ????????????
        if (CollectionUtils.isNotEmpty(info.getInsertList())) {
            List<${entity}> insertList = convertList(info.getInsertList(), ${entity}.class);
            insertList.forEach(data -> {
                data.setRemark(data.getRemark() == null ? "" : data.getRemark());
                data.setOptVersion(0);
            });
            ${table.mapperName?uncap_first}.insertBatchSomeColumn(insertList);
        }

        // ????????????
        if (CollectionUtils.isNotEmpty(info.getUpdateList())) {
            info.getUpdateList().forEach(this::update);
        }
    }

     /**
      * ??????${table.comment!}??????????????????
      *
      * @param param ${table.comment!}????????????
      * @return ${table.comment!}???????????????
      */
     @Override
     public PageInfo<${table.dtoDetailResponseName}> page(${table.dtoQueryName} param) {
        if (param.getPageNo() != null && param.getPageSize() != null) {
            PageMethod.startPage(param.getPageNo(), param.getPageSize());
        }
        LambdaQueryWrapper<${entity}> mapper = queryWrapper(param);
        mapper.orderByDesc(CreditReportBaseModel::getId);

        PageInfo<${entity}> pageInfo = new PageInfo<>(${table.mapperName?uncap_first}.selectList(mapper));
        return PageUtil.convertTo(pageInfo, ${table.dtoDetailResponseName}.class);
     }

    /**
     * ??????${table.comment!}????????????
     *
     * @param param ${table.comment!}????????????
     * @return ${table.comment!}?????????
     */
    @Override
    public List<${table.dtoDetailResponseName}> list(${table.dtoQueryName} param) {
        LambdaQueryWrapper<${entity}> mapper = queryWrapper(param);
        mapper.orderByDesc(CreditReportBaseModel::getId);

        List<${entity}> list = ${table.mapperName?uncap_first}.selectList(mapper);
        return convertList(list,${table.dtoDetailResponseName}.class);
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
    * ??????${fieldComment}??????${table.comment!}?????????
    *
    * @param ${field.propertyName}s ${field.comment}
    * @return ${table.comment!}?????????
    */
    @Override
    public List<${entity}> getInfoBy${field.propertyName?cap_first}s(Collection<String> ${field.propertyName}s){
        String errorDesc = "${fieldComment}????????????";

        if (CollectionUtils.isEmpty(${field.propertyName}s)) {
            throw new BusinessException("E", errorDesc);
        }

        ${field.propertyName}s.forEach(data -> {
            if (data == null) {
                throw new BusinessException("E", errorDesc);
            }
        });

        LambdaQueryWrapper<${entity}> lambdaQuery = Wrappers.<${entity}>lambdaQuery()
            .in(${entity}::get${field.propertyName?cap_first}, ${field.propertyName}s);

        return ${table.mapperName?uncap_first}.selectList(lambdaQuery);
    }

    </#if>
</#list>

    /**
     * ??????${table.comment!}????????????
     *
     * @param param ${table.comment!}????????????
     * @return ${table.comment!}???????????????
     */
    @Override
    public int count(${table.dtoQueryName} param) {
        return ${table.mapperName?uncap_first}.selectCount(queryWrapper(param));
    }

    /**
     * ??????${table.comment!}????????????
     *
     * @param param ${table.comment!}????????????????????????
     * @return ${table.comment!}????????????
     */
    @Override
    public ${table.dtoDetailResponseName} detail(${table.dtoDetailRequestName} param) {
        ${entity} ${entity?uncap_first} = ${entity?uncap_first}Mapper.selectById(param.get${entity}Id());
        return convertObject(${entity?uncap_first},${table.dtoDetailResponseName}.class);
    }

    /**
     * ??????${table.comment!}??????
     *
     * @param param ${table.comment!}??????????????????
     * @return ${table.comment!}??????
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(${table.dtoDeleteRequestName} param) {
        return ${entity?uncap_first}Mapper.deleteById(param.get${entity}Id());
    }

    private LambdaQueryWrapper<${entity}> queryWrapper(${table.dtoQueryName} param) {
        LambdaQueryWrapper<${entity}> mapper = Wrappers.lambdaQuery(convertObject(param,${entity}.class));
        mapper.ge(param.getQueryStartDate() != null ,CreditReportBaseModel::getCreateTime,CreditReportUtil.wrapStartDateTime(param.getQueryStartDate()));
        mapper.le(param.getQueryEndDate()!= null ,CreditReportBaseModel::getCreateTime,CreditReportUtil.wrapEndDateTime(param.getQueryEndDate()));
        return mapper;
    }

}
