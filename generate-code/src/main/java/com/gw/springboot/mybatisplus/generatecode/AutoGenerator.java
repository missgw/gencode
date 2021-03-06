/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gw.springboot.mybatisplus.generatecode;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.gw.springboot.mybatisplus.generatecode.config.DataSourceConfig;
import com.gw.springboot.mybatisplus.generatecode.config.GlobalConfig;
import com.gw.springboot.mybatisplus.generatecode.config.PackageConfig;
import com.gw.springboot.mybatisplus.generatecode.config.StrategyConfig;
import com.gw.springboot.mybatisplus.generatecode.config.TemplateConfig;
import com.gw.springboot.mybatisplus.generatecode.config.*;
import com.gw.springboot.mybatisplus.generatecode.config.builder.ConfigBuilder;
import com.gw.springboot.mybatisplus.generatecode.config.po.TableField;
import com.gw.springboot.mybatisplus.generatecode.config.po.TableInfo;
import com.gw.springboot.mybatisplus.generatecode.engine.AbstractTemplateEngine;
import com.gw.springboot.mybatisplus.generatecode.engine.VelocityTemplateEngine;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ????????????
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-30
 */
@Data
@Accessors(chain = true)
public class AutoGenerator {
    private static final Logger logger = LoggerFactory.getLogger(AutoGenerator.class);

    /**
     * ????????????
     */
    protected ConfigBuilder config;
    /**
     * ????????????
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    protected InjectionConfig injectionConfig;
    /**
     * ???????????????
     */
    private DataSourceConfig dataSource;
    /**
     * ??????????????????
     */
    private StrategyConfig strategy;
    /**
     * ??? ????????????
     */
    private PackageConfig packageInfo;
    /**
     * ?????? ????????????
     */
    private TemplateConfig template;
    /**
     * ?????? ????????????
     */
    private GlobalConfig globalConfig;
    /**
     * ????????????
     */
    private AbstractTemplateEngine templateEngine;

    /**
     * ????????????
     */
    public void execute() {
        logger.debug("==========================??????????????????...==========================");
        // ???????????????
        if (null == config) {
            config = new ConfigBuilder(packageInfo, dataSource, strategy, template, globalConfig);
            if (null != injectionConfig) {
                injectionConfig.setConfig(config);
            }
        }
        if (null == templateEngine) {
            // ????????????????????????????????? Velocity ?????? ??? ?????? ???
            templateEngine = new VelocityTemplateEngine();
        }
        // ???????????????????????????????????????
        templateEngine.init(this.pretreatmentConfigBuilder(config)).mkdirs().batchOutput().open();
        logger.debug("==========================???????????????????????????==========================");
    }

    /**
     * ????????????????????????????????????
     *
     * @param config ????????????
     * @return ignore
     */
    protected List<TableInfo> getAllTableInfoList(ConfigBuilder config) {
        return config.getTableInfoList();
    }

    /**
     * ???????????????
     *
     * @param config ???????????????
     * @return ?????????????????????
     */
    protected ConfigBuilder pretreatmentConfigBuilder(ConfigBuilder config) {
        /*
         * ?????????????????????
         */
        if (null != injectionConfig) {
            injectionConfig.initMap();
            config.setInjectionConfig(injectionConfig);
        }
        /*
         * ???????????????
         */
        List<TableInfo> tableList = this.getAllTableInfoList(config);
        for (TableInfo tableInfo : tableList) {
            /* ---------- ??????????????? ---------- */
            if (config.getGlobalConfig().isActiveRecord()) {
                // ?????? ActiveRecord ??????
                tableInfo.setImportPackages(Model.class.getCanonicalName());
            }
            if (tableInfo.isConvert()) {
                // ?????????
                tableInfo.setImportPackages(TableName.class.getCanonicalName());
            }
            if (config.getStrategyConfig().getLogicDeleteFieldName() != null && tableInfo.isLogicDelete(config.getStrategyConfig().getLogicDeleteFieldName())) {
                // ??????????????????
                tableInfo.setImportPackages(TableLogic.class.getCanonicalName());
            }
            if (StringUtils.isNotBlank(config.getStrategyConfig().getVersionFieldName())) {
                // ???????????????
                tableInfo.setImportPackages(Version.class.getCanonicalName());
            }
            boolean importSerializable = true;
            if (StringUtils.isNotBlank(config.getSuperEntityClass())) {
                // ?????????
                tableInfo.setImportPackages(config.getSuperEntityClass());
                importSerializable = false;
            }
            if (config.getGlobalConfig().isActiveRecord()) {
                importSerializable = true;
            }
            if (importSerializable) {
                tableInfo.setImportPackages(Serializable.class.getCanonicalName());
            }
            // Boolean??????is????????????
            if (config.getStrategyConfig().isEntityBooleanColumnRemoveIsPrefix()
                && CollectionUtils.isNotEmpty(tableInfo.getFields())) {
                List<TableField> tableFields = tableInfo.getFields().stream().filter(field -> "boolean".equalsIgnoreCase(field.getPropertyType()))
                    .filter(field -> field.getPropertyName().startsWith("is")).collect(Collectors.toList());
                tableFields.forEach(field -> {
                    //?????????is?????????????????????????????????.
                    if (field.isKeyFlag()) {
                        tableInfo.setImportPackages(TableId.class.getCanonicalName());
                    } else {
                        tableInfo.setImportPackages(com.baomidou.mybatisplus.annotation.TableField.class.getCanonicalName());
                    }
                    field.setConvert(true);
                    field.setPropertyName(StringUtils.removePrefixAfterPrefixToLower(field.getPropertyName(), 2));
                });
            }
        }
        return config.setTableInfoList(tableList);
    }

    public InjectionConfig getCfg() {
        return injectionConfig;
    }

    public AutoGenerator setCfg(InjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
        return this;
    }
}
