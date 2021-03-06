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
package com.gw.springboot.mybatisplus.generatecode.config.builder;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gw.springboot.mybatisplus.generatecode.InjectionConfig;
import com.gw.springboot.mybatisplus.generatecode.config.ConstVal;
import com.gw.springboot.mybatisplus.generatecode.config.DataSourceConfig;
import com.gw.springboot.mybatisplus.generatecode.config.GlobalConfig;
import com.gw.springboot.mybatisplus.generatecode.config.IDbQuery;
import com.gw.springboot.mybatisplus.generatecode.config.IKeyWordsHandler;
import com.gw.springboot.mybatisplus.generatecode.config.INameConvert;
import com.gw.springboot.mybatisplus.generatecode.config.PackageConfig;
import com.gw.springboot.mybatisplus.generatecode.config.StrategyConfig;
import com.gw.springboot.mybatisplus.generatecode.config.TemplateConfig;
import com.gw.springboot.mybatisplus.generatecode.config.querys.H2Query;
import com.gw.springboot.mybatisplus.generatecode.config.*;
import com.gw.springboot.mybatisplus.generatecode.config.po.TableField;
import com.gw.springboot.mybatisplus.generatecode.config.po.TableFill;
import com.gw.springboot.mybatisplus.generatecode.config.po.TableInfo;
import com.gw.springboot.mybatisplus.generatecode.config.rules.NamingStrategy;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * ???????????? ???????????????????????????
 *
 * @author YangHu, tangguo, hubin, Juzi
 * @since 2016-08-30
 */
public class ConfigBuilder {

    /**
     * ????????????????????????
     */
    private final TemplateConfig template;
    /**
     * ???????????????
     */
    private final DataSourceConfig dataSourceConfig;
    /**
     * SQL??????
     */
    private Connection connection;
    /**
     * SQL????????????
     */
    @Deprecated
    private IDbQuery dbQuery;

    @Deprecated
    private DbType dbType;
    private String superEntityClass;
    private String superMapperClass;
    /**
     * service????????????
     */
    private String superServiceClass;

    private String superServiceImplClass;
    private String superControllerClass;
    /**
     * ??????????????????
     */
    private List<TableInfo> tableInfoList;
    /**
     * ???????????????
     */
    private Map<String, String> packageInfo;
    /**
     * ??????????????????
     */
    private Map<String, String> pathInfo;
    /**
     * ????????????
     */
    private StrategyConfig strategyConfig;
    /**
     * ??????????????????
     */
    private GlobalConfig globalConfig;
    /**
     * ??????????????????
     */
    private InjectionConfig injectionConfig;
    /**
     * ??????????????????
     */
    @Deprecated
    private boolean commentSupported;
    /**
     * ????????????
     */
    private static final Pattern REGX = Pattern.compile("[~!/@#$%^&*()-_=+\\\\|[{}];:'\",<.>?]+");

    /**
     * ???????????????????????????
     *
     * @param packageConfig    ?????????
     * @param dataSourceConfig ???????????????
     * @param strategyConfig   ?????????
     * @param template         ????????????
     * @param globalConfig     ????????????
     */
    public ConfigBuilder(
            PackageConfig packageConfig,
            DataSourceConfig dataSourceConfig,
            StrategyConfig strategyConfig,
            TemplateConfig template,
            GlobalConfig globalConfig) {
        // ????????????
        this.globalConfig = Optional.ofNullable(globalConfig).orElseGet(GlobalConfig::new);
        // ????????????
        this.template = Optional.ofNullable(template).orElseGet(TemplateConfig::new);
        // ?????????
        if (null == packageConfig) {
            handlerPackage(this.template, this.globalConfig.getOutputDir(), this.globalConfig.getDtoOutputDir(), new PackageConfig());
        } else {
            handlerPackage(this.template, this.globalConfig.getOutputDir(), this.globalConfig.getDtoOutputDir(), packageConfig);
        }
        this.dataSourceConfig = dataSourceConfig;
        handlerDataSource(dataSourceConfig);
        // ????????????
        this.strategyConfig = Optional.ofNullable(strategyConfig).orElseGet(StrategyConfig::new);
        // SQLITE ??????????????????????????????
        commentSupported = !dataSourceConfig.getDbType().equals(DbType.SQLITE);

        handlerStrategy(this.strategyConfig);
    }

    // ************************ ???????????? BEGIN*****************************

    /**
     * ?????????????????????
     *
     * @return ?????????
     */
    public Map<String, String> getPackageInfo() {
        return packageInfo;
    }

    /**
     * ??????????????????
     *
     * @return ????????????
     */
    public Map<String, String> getPathInfo() {
        return pathInfo;
    }

    public String getSuperEntityClass() {
        return superEntityClass;
    }

    public String getSuperMapperClass() {
        return superMapperClass;
    }

    /**
     * ??????????????????
     *
     * @return ??????????????????
     */
    public String getSuperServiceClass() {
        return superServiceClass;
    }

    public String getSuperServiceImplClass() {
        return superServiceImplClass;
    }

    public String getSuperControllerClass() {
        return superControllerClass;
    }

    /**
     * ?????????
     *
     * @return ???????????????
     */
    public List<TableInfo> getTableInfoList() {
        return tableInfoList;
    }

    public ConfigBuilder setTableInfoList(List<TableInfo> tableInfoList) {
        this.tableInfoList = tableInfoList;
        return this;
    }

    /**
     * ????????????????????????
     *
     * @return ??????????????????????????????
     */
    public TemplateConfig getTemplate() {
        return this.template;
    }

    // ****************************** ???????????? END**********************************

    /**
     * ???????????????
     *
     * @param template  TemplateConfig
     * @param outputDir ????????????
     * @param config    PackageConfig
     */
    private void handlerPackage(TemplateConfig template, String outputDir, String dtoOutputDir, PackageConfig config) {
        // ?????????
        packageInfo = CollectionUtils.newHashMapWithExpectedSize(11);
        packageInfo.put(ConstVal.MODULE_NAME, config.getModuleName());
        packageInfo.put(ConstVal.ENTITY, joinPackage(config.getParent(), config.getEntity()));
        packageInfo.put(ConstVal.MAPPER, joinPackage(config.getParent(), config.getMapper()));

        if (StringUtils.isBlank(globalConfig.getXmlOutputDir())) {
            packageInfo.put(ConstVal.XML, joinPackage(config.getParent(), config.getXml()));
        } else {
            // ?????????xml????????????
            packageInfo.put(ConstVal.XML, config.getXml());
        }
        packageInfo.put(ConstVal.SERVICE, joinPackage(config.getParent(), config.getService()));
        packageInfo.put(
                ConstVal.SERVICE_IMPL, joinPackage(config.getParent(), config.getServiceImpl()));
        packageInfo.put(ConstVal.CONTROLLER, joinPackage(config.getParent(), config.getController()));

        // self add
        packageInfo.put(ConstVal.DTO_ADD, joinPackage(config.getParent(), config.getDtoRequest()));
        packageInfo.put(ConstVal.DTO_QUERY, joinPackage(config.getParent(), config.getDtoRequest()));
        packageInfo.put(ConstVal.DTO_UPDATE, joinPackage(config.getParent(), config.getDtoRequest()));
        packageInfo.put(ConstVal.DTO_DETAIL_REQUEST, joinPackage(config.getParent(), config.getDtoRequest()));
        packageInfo.put(ConstVal.DTO_DETAIL_RESPONSE, joinPackage(config.getParent(), config.getDtoResponse()));
        packageInfo.put(ConstVal.DTO_DELETE_REQUEST, joinPackage(config.getParent(), config.getDtoRequest()));
        packageInfo.put(ConstVal.FEIGN_CLIENT, joinPackage(config.getParent(), config.getFeignClient()));
        packageInfo.put(ConstVal.FEIGN_CLIENT_CONST, joinPackage(config.getParent(), config.getFeignClient()));

        if(globalConfig.isEnableEnum()){
            packageInfo.put(ConstVal.ENUMS,joinPackage(config.getParent(),config.getEnums()));
        }

        // self add
        if (StringUtils.isNotBlank(config.getModuleName())) {
            packageInfo.put(ConstVal.PARENT, config.getParent().replace("\\" + StringPool.DOT + config.getModuleName(), ""));
        } else {
            packageInfo.put(ConstVal.PARENT, config.getParent());
        }
        packageInfo.put(ConstVal.BASE_PARENT_PKG, config.getBaseParentPkg());


        // ???????????????
        Map<String, String> configPathInfo = config.getPathInfo();
        if (null != configPathInfo) {
            pathInfo = configPathInfo;
        } else {
            // ??????????????????
            pathInfo = CollectionUtils.newHashMapWithExpectedSize(10);
            // entity
            setPathInfo(pathInfo, template.getEntity(getGlobalConfig().isKotlin()), outputDir, ConstVal.ENTITY_PATH, ConstVal.ENTITY);
            // mapper
            setPathInfo(pathInfo, template.getMapper(), outputDir, ConstVal.MAPPER_PATH, ConstVal.MAPPER);
            // mapper xml
            if (StringUtils.isBlank(globalConfig.getXmlOutputDir())) {
                // ??????mapper xml????????????
                setPathInfo(pathInfo, template.getXml(), outputDir, ConstVal.XML_PATH, ConstVal.XML);
            } else {
                // ?????????xml????????????
                setPathInfo(pathInfo, template.getXml(), globalConfig.getXmlOutputDir(), ConstVal.XML_PATH, ConstVal.XML);
            }

            // service
            setPathInfo(pathInfo, template.getService(), outputDir, ConstVal.SERVICE_PATH, ConstVal.SERVICE);

            // serviceImpl
            setPathInfo(pathInfo, template.getServiceImpl(), outputDir, ConstVal.SERVICE_IMPL_PATH, ConstVal.SERVICE_IMPL);

            // controller
            setPathInfo(pathInfo, template.getController(), outputDir, ConstVal.CONTROLLER_PATH, ConstVal.CONTROLLER);

            // add update query ????????????
            setPathInfo(pathInfo, template.getDtoAdd(), dtoOutputDir, ConstVal.DTO_REQUEST_PATH, ConstVal.DTO_ADD);

            // dto ????????????
            setPathInfo(pathInfo, template.getDtoDetailResponse(), dtoOutputDir, ConstVal.DTO_RESPONSE_PATH, ConstVal.DTO_DETAIL_RESPONSE);

            // enums ?????????
            if(globalConfig.isEnableEnum()){
                setPathInfo(pathInfo, template.getEnums(), dtoOutputDir, ConstVal.ENUMS_PATH, ConstVal.ENUMS);
            }


//            // feign client
//
//            setPathInfo(
//                    pathInfo,
//                    template.getFeignClient(),
//                    outputDir,
//                    ConstVal.FEIGN_CLIENT_PATH,
//                    ConstVal.FEIGN_CLIENT);
//            // feign client const
//            setPathInfo(
//                    pathInfo,
//                    template.getFeignClientConst(),
//                    outputDir,
//                    ConstVal.FEIGN_CLIENT_PATH,
//                    ConstVal.FEIGN_CLIENT_CONST);
        }
    }

    /**
     * ???????????????????????????
     *
     * @param pathInfo
     * @param template
     * @param outputDir
     * @param path
     * @param module
     */
    private void setPathInfo(
            Map<String, String> pathInfo, String template, String outputDir, String path, String module) {
        if (StringUtils.isNotBlank(template)) {
            pathInfo.put(path, joinPath(outputDir, packageInfo.get(module)));
        }
    }

    /**
     * ?????????????????????
     *
     * @param config DataSourceConfig
     */
    private void handlerDataSource(DataSourceConfig config) {
        connection = config.getConn();
        dbType = config.getDbType();
        dbQuery = config.getDbQuery();
    }

    /**
     * ?????????????????? ????????????????????????????????????????????????
     *
     * @param config StrategyConfig
     */
    private void handlerStrategy(StrategyConfig config) {
        processTypes(config);
        tableInfoList = getTablesInfo(config);
    }

    /**
     * ??????superClassName,IdClassType,IdStrategy??????
     *
     * @param config ????????????
     */
    private void processTypes(StrategyConfig config) {
        this.superServiceClass =
                getValueOrDefault(config.getSuperServiceClass(), ConstVal.SUPER_SERVICE_CLASS);
        this.superServiceImplClass =
                getValueOrDefault(config.getSuperServiceImplClass(), ConstVal.SUPER_SERVICE_IMPL_CLASS);
        this.superMapperClass =
                getValueOrDefault(config.getSuperMapperClass(), ConstVal.SUPER_MAPPER_CLASS);
        superEntityClass = config.getSuperEntityClass();
        superControllerClass = config.getSuperControllerClass();
    }

    private static String getValueOrDefault(String value, String defaultValue) {
        return StringUtils.isBlank(value) ? defaultValue : value;
    }

    /**
     * ???????????????????????????
     *
     * @param tableList ?????????
     * @param config    ???????????????
     * @return ???????????????????????????
     * @deprecated 3.3.2
     */
    @Deprecated
    private List<TableInfo> processTable(
            List<TableInfo> tableList, NamingStrategy strategy, StrategyConfig config) {
        return processTable(tableList, config);
    }

    /**
     * ???????????????????????????
     *
     * @param tableList ?????????
     * @param config    ???????????????
     * @return ???????????????????????????
     */
    private List<TableInfo> processTable(List<TableInfo> tableList, StrategyConfig config) {
        String[] tablePrefix = config.getTablePrefix();
        for (TableInfo tableInfo : tableList) {
            String entityName;
            INameConvert nameConvert = strategyConfig.getNameConvert();
            if (null != nameConvert) {
                // ???????????????????????????
                entityName = nameConvert.entityNameConvert(tableInfo);
            } else {
                entityName =
                        NamingStrategy.capitalFirst(
                                processName(tableInfo.getName(), config.getNaming(), tablePrefix));
            }

            if (StringUtils.isNotBlank(globalConfig.getEntityName())) {
                tableInfo.setConvert(true);
                tableInfo.setEntityName(String.format(globalConfig.getEntityName(), entityName));
            } else {
                tableInfo.setEntityName(strategyConfig, entityName);
            }
            if (StringUtils.isNotBlank(globalConfig.getMapperName())) {
                tableInfo.setMapperName(String.format(globalConfig.getMapperName(), entityName));
            } else {
                tableInfo.setMapperName(entityName + ConstVal.MAPPER);
            }
            if (StringUtils.isNotBlank(globalConfig.getXmlName())) {
                tableInfo.setXmlName(String.format(globalConfig.getXmlName(), entityName));
            } else {
                tableInfo.setXmlName(entityName + ConstVal.MAPPER);
            }
            if (StringUtils.isNotBlank(globalConfig.getServiceName())) {
                tableInfo.setServiceName(String.format(globalConfig.getServiceName(), entityName));
            } else {
                tableInfo.setServiceName("I" + entityName + ConstVal.SERVICE);
            }
            if (StringUtils.isNotBlank(globalConfig.getServiceImplName())) {
                tableInfo.setServiceImplName(String.format(globalConfig.getServiceImplName(), entityName));
            } else {
                tableInfo.setServiceImplName(entityName + ConstVal.SERVICE_IMPL);
            }
            if (StringUtils.isNotBlank(globalConfig.getControllerName())) {
                tableInfo.setControllerName(String.format(globalConfig.getControllerName(), entityName));
            } else {
                tableInfo.setControllerName(entityName + ConstVal.CONTROLLER);
            }

            // self add
            if (StringUtils.isNotBlank(globalConfig.getDtoAddName())) {
                tableInfo.setDtoAddName(String.format(globalConfig.getDtoAddName(), entityName));
            } else {
                tableInfo.setDtoAddName(entityName + ConstVal.DTO_ADD);
            }

            // list query
            if (StringUtils.isNotBlank(globalConfig.getDtoQueryName())) {
                tableInfo.setDtoQueryName(String.format(globalConfig.getDtoQueryName(), entityName));
            } else {
                tableInfo.setDtoQueryName(entityName + ConstVal.DTO_QUERY);
            }

            // update
            if (StringUtils.isNotBlank(globalConfig.getDtoUpdateName())) {
                tableInfo.setDtoUpdateName(String.format(globalConfig.getDtoUpdateName(), entityName));
            } else {
                tableInfo.setDtoUpdateName(entityName + ConstVal.DTO_UPDATE);
            }

            // detail request
            if (StringUtils.isNotBlank(globalConfig.getDtoDetailRequestName())) {
                tableInfo.setDtoDetailRequestName(String.format(globalConfig.getDtoDetailRequestName(), entityName));
            } else {
                tableInfo.setDtoDetailRequestName(entityName + ConstVal.DTO_DETAIL_REQUEST);
            }

            // detail response
            if (StringUtils.isNotBlank(globalConfig.getDtoDetailResponseName())) {
                tableInfo.setDtoDetailResponseName(String.format(globalConfig.getDtoDetailResponseName(), entityName));
            } else {
                tableInfo.setDtoDetailResponseName(entityName + ConstVal.DTO_DETAIL_RESPONSE);
            }

            // delete request
            if (StringUtils.isNotBlank(globalConfig.getDtoDeleteRequestName())) {
                tableInfo.setDtoDeleteRequestName(String.format(globalConfig.getDtoDeleteRequestName(), entityName));
            } else {
                tableInfo.setDtoDeleteRequestName(entityName + ConstVal.DTO_DELETE_REQUEST);
            }

            // feign client
            if (StringUtils.isNotBlank(globalConfig.getFeignClientName())) {
                tableInfo.setFeignClientName(String.format(globalConfig.getFeignClientName(), entityName));
            } else {
                tableInfo.setFeignClientName(entityName + ConstVal.FEIGN_CLIENT);
            }

            // feign client const
            if (StringUtils.isNotBlank(globalConfig.getFeignClientConstName())) {
                tableInfo.setFeignClientConstName(
                        String.format(
                                globalConfig.getFeignClientConstName(),
                                org.apache.commons.lang3.StringUtils.capitalize(
                                        packageInfo.get(ConstVal.MODULE_NAME))));
            } else {
                tableInfo.setFeignClientConstName(
                        org.apache.commons.lang3.StringUtils.capitalize(packageInfo.get(ConstVal.MODULE_NAME))
                                + ConstVal.FEIGN_CLIENT_CONST);
            }

            // enums
            if(globalConfig.isEnableEnum()){
                if (StringUtils.isNotBlank(globalConfig.getEnumsName())) {
                    tableInfo.setEnumsName(String.format(globalConfig.getEnumsName(), entityName));
                } else {
                    tableInfo.setEnumsName(entityName + ConstVal.ENUMS);
                }
            }


            // ???????????????
            checkImportPackages(tableInfo);
        }
        return tableList;
    }

    /**
     * ???????????????
     *
     * @param tableInfo ignore
     */
    private void checkImportPackages(TableInfo tableInfo) {
        if (StringUtils.isNotBlank(strategyConfig.getSuperEntityClass())) {
            // ???????????????
            tableInfo.getImportPackages().add(strategyConfig.getSuperEntityClass());
        } else if (globalConfig.isActiveRecord()) {
            // ??????????????? AR ??????
            tableInfo
                    .getImportPackages()
                    .add(com.baomidou.mybatisplus.extension.activerecord.Model.class.getCanonicalName());
        }
        if (null != globalConfig.getIdType() && tableInfo.isHavePrimaryKey()) {
            // ???????????? IdType ??????
            tableInfo
                    .getImportPackages()
                    .add(com.baomidou.mybatisplus.annotation.IdType.class.getCanonicalName());
            tableInfo
                    .getImportPackages()
                    .add(com.baomidou.mybatisplus.annotation.TableId.class.getCanonicalName());
        }
        if (StringUtils.isNotBlank(strategyConfig.getVersionFieldName())
                && CollectionUtils.isNotEmpty(tableInfo.getFields())) {
            tableInfo
                    .getFields()
                    .forEach(
                            f -> {
                                if (strategyConfig.getVersionFieldName().equals(f.getName())) {
                                    tableInfo
                                            .getImportPackages()
                                            .add(com.baomidou.mybatisplus.annotation.Version.class.getCanonicalName());
                                }
                            });
        }
    }

    /**
     * ?????????????????????????????????
     */
    private List<TableInfo> getTablesInfo(StrategyConfig config) {
        boolean isInclude = (null != config.getInclude() && config.getInclude().length > 0);
        boolean isExclude = (null != config.getExclude() && config.getExclude().length > 0);
        if (isInclude && isExclude) {
            throw new RuntimeException("<strategy> ????????? <include> ??? <exclude> ?????????????????????");
        }
        if (config.getNotLikeTable() != null && config.getLikeTable() != null) {
            throw new RuntimeException("<strategy> ????????? <likeTable> ??? <notLikeTable> ?????????????????????");
        }
        // ??????????????????
        List<TableInfo> tableList = new ArrayList<>();

        // ???????????????????????????????????????
        List<TableInfo> includeTableList = new ArrayList<>();
        List<TableInfo> excludeTableList = new ArrayList<>();

        // ??????????????????
        Set<String> notExistTables = new HashSet<>();
        DbType dbType = this.dataSourceConfig.getDbType();
        IDbQuery dbQuery = this.dataSourceConfig.getDbQuery();
        try {
            String tablesSql = dataSourceConfig.getDbQuery().tablesSql();
            if (DbType.POSTGRE_SQL == dbType) {
                String schema = dataSourceConfig.getSchemaName();
                if (schema == null) {
                    // pg ?????? schema=public
                    schema = "public";
                    dataSourceConfig.setSchemaName(schema);
                }
                tablesSql = String.format(tablesSql, schema);
            } else if (DbType.KINGBASE_ES == dbType) {
                String schema = dataSourceConfig.getSchemaName();
                if (schema == null) {
                    // kingbase ?????? schema=PUBLIC
                    schema = "PUBLIC";
                    dataSourceConfig.setSchemaName(schema);
                }
                tablesSql = String.format(tablesSql, schema);
            } else if (DbType.DB2 == dbType) {
                String schema = dataSourceConfig.getSchemaName();
                if (schema == null) {
                    // db2 ?????? schema=current schema
                    schema = "current schema";
                    dataSourceConfig.setSchemaName(schema);
                }
                tablesSql = String.format(tablesSql, schema);
            }
            // oracle?????????????????????????????????????????????
            else if (DbType.ORACLE == dbType) {
                String schema = dataSourceConfig.getSchemaName();
                // oracle ?????? schema=username
                if (schema == null) {
                    schema = dataSourceConfig.getUsername().toUpperCase();
                    dataSourceConfig.setSchemaName(schema);
                }
                tablesSql = String.format(tablesSql, schema);
            }
            StringBuilder sql = new StringBuilder(tablesSql);
            if (config.isEnableSqlFilter()) {
                if (config.getLikeTable() != null) {
                    sql.append(" AND ")
                            .append(dbQuery.tableName())
                            .append(" LIKE '")
                            .append(config.getLikeTable().getValue())
                            .append("'");
                } else if (config.getNotLikeTable() != null) {
                    sql.append(" AND ")
                            .append(dbQuery.tableName())
                            .append(" NOT LIKE '")
                            .append(config.getNotLikeTable().getValue())
                            .append("'");
                }
                if (isInclude) {
                    sql.append(" AND ")
                            .append(dbQuery.tableName())
                            .append(" IN (")
                            .append(
                                    Arrays.stream(config.getInclude())
                                            .map(tb -> "'" + tb + "'")
                                            .collect(Collectors.joining(",")))
                            .append(")");
                } else if (isExclude) {
                    sql.append(" AND ")
                            .append(dbQuery.tableName())
                            .append(" NOT IN (")
                            .append(
                                    Arrays.stream(config.getExclude())
                                            .map(tb -> "'" + tb + "'")
                                            .collect(Collectors.joining(",")))
                            .append(")");
                }
            }
            TableInfo tableInfo;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
                 ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    final String tableName = results.getString(dbQuery.tableName());
                    if (StringUtils.isBlank(tableName)) {
                        System.err.println("??????????????????????????????");
                        continue;
                    }
                    tableInfo = new TableInfo();
                    tableInfo.setName(tableName);
                    String commentColumn = dbQuery.tableComment();
                    if (StringUtils.isNotBlank(commentColumn)) {
                        String tableComment = results.getString(commentColumn);
                        if (config.isSkipView() && "VIEW".equals(tableComment)) {
                            // ????????????
                            continue;
                        }
                        tableInfo.setComment(formatComment(tableComment));
                    }

                    if (isInclude) {
                        for (String includeTable : config.getInclude()) {
                            // ????????????????????? ??? ?????? true
                            if (tableNameMatches(includeTable, tableName)) {
                                includeTableList.add(tableInfo);
                            } else {
                                // ??????????????????
                                if (!REGX.matcher(includeTable).find()) {
                                    notExistTables.add(includeTable);
                                }
                            }
                        }
                    } else if (isExclude) {
                        for (String excludeTable : config.getExclude()) {
                            // ????????????????????? ??? ?????? true
                            if (tableNameMatches(excludeTable, tableName)) {
                                excludeTableList.add(tableInfo);
                            } else {
                                // ??????????????????
                                if (!REGX.matcher(excludeTable).find()) {
                                    notExistTables.add(excludeTable);
                                }
                            }
                        }
                    }
                    tableList.add(tableInfo);
                }
            }
            // ?????????????????????????????????????????????????????????????????????
            for (TableInfo tabInfo : tableList) {
                notExistTables.remove(tabInfo.getName());
            }
            if (notExistTables.size() > 0) {
                System.err.println("??? " + notExistTables + " ?????????????????????????????????");
            }

            // ??????????????????????????????
            if (isExclude) {
                tableList.removeAll(excludeTableList);
                includeTableList = tableList;
            }
            if (!isInclude && !isExclude) {
                includeTableList = tableList;
            }
            // ?????????????????????????????????????????? github issues/219
            includeTableList.forEach(ti -> convertTableFields(ti, config));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return processTable(includeTableList, config);
    }

    /**
     * ????????????
     *
     * @param setTableName ????????????
     * @param dbTableName  ???????????????
     * @return ignore
     */
    private boolean tableNameMatches(String setTableName, String dbTableName) {
        return setTableName.equalsIgnoreCase(dbTableName)
                || StringUtils.matches(setTableName, dbTableName);
    }

    /**
     * ?????????????????????????????????
     *
     * @param tableInfo ?????????
     * @param config    ????????????
     * @return ignore
     */
    private TableInfo convertTableFields(TableInfo tableInfo, StrategyConfig config) {
        boolean haveId = false;
        List<TableField> fieldList = new ArrayList<>();
        List<TableField> commonFieldList = new ArrayList<>();
        DbType dbType = this.dataSourceConfig.getDbType();
        IDbQuery dbQuery = dataSourceConfig.getDbQuery();
        String tableName = tableInfo.getName();
        try {
            String tableFieldsSql = dbQuery.tableFieldsSql();
            Set<String> h2PkColumns = new HashSet<>();
            if (DbType.POSTGRE_SQL == dbType) {
                tableFieldsSql = String.format(tableFieldsSql, dataSourceConfig.getSchemaName(), tableName);
            } else if (DbType.KINGBASE_ES == dbType) {
                tableFieldsSql = String.format(tableFieldsSql, dataSourceConfig.getSchemaName(), tableName);
            } else if (DbType.OSCAR == dbType) {
                tableFieldsSql = String.format(tableFieldsSql, tableName);
            } else if (DbType.DB2 == dbType) {
                tableFieldsSql = String.format(tableFieldsSql, dataSourceConfig.getSchemaName(), tableName);
            } else if (DbType.ORACLE == dbType) {
                tableName = tableName.toUpperCase();
                tableFieldsSql =
                        String.format(
                                tableFieldsSql.replace("#schema", dataSourceConfig.getSchemaName()), tableName);
            } else if (DbType.DM == dbType) {
                tableName = tableName.toUpperCase();
                tableFieldsSql = String.format(tableFieldsSql, tableName);
            } else if (DbType.H2 == dbType) {
                try (PreparedStatement pkQueryStmt =
                             connection.prepareStatement(String.format(H2Query.PK_QUERY_SQL, tableName));
                     ResultSet pkResults = pkQueryStmt.executeQuery()) {
                    while (pkResults.next()) {
                        String primaryKey = pkResults.getString(dbQuery.fieldKey());
                        if (Boolean.parseBoolean(primaryKey)) {
                            h2PkColumns.add(pkResults.getString(dbQuery.fieldName()));
                        }
                    }
                }
                tableFieldsSql = String.format(tableFieldsSql, tableName);
            } else {
                tableFieldsSql = String.format(tableFieldsSql, tableName);
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(tableFieldsSql);
                 ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    TableField field = new TableField();
                    String columnName = results.getString(dbQuery.fieldName());
                    // ??????????????????????????????????????????????????????ID????????????list???????????????0?????????
                    boolean isId;
                    if (DbType.H2 == dbType) {
                        isId = h2PkColumns.contains(columnName);
                    } else {
                        String key = results.getString(dbQuery.fieldKey());
                        if (DbType.DB2 == dbType || DbType.SQLITE == dbType) {
                            isId = StringUtils.isNotBlank(key) && "1".equals(key);
                        } else {
                            isId = StringUtils.isNotBlank(key) && "PRI".equals(key.toUpperCase());
                            // ???????????????
                            if (DbType.MYSQL == dbType && "MUL".equals(key)) {
                                field.setHasIndex(true);
                            }
                        }
                    }

                    // ??????ID
                    if (isId && !haveId) {
                        haveId = true;
                        field.setKeyFlag(true);
                        tableInfo.setHavePrimaryKey(true);
                        field.setKeyIdentityFlag(dbQuery.isKeyIdentity(results));
                    } else {
                        field.setKeyFlag(false);
                    }
                    // ?????????????????????
                    String[] fcs = dbQuery.fieldCustom();
                    if (null != fcs) {
                        Map<String, Object> customMap = CollectionUtils.newHashMapWithExpectedSize(fcs.length);
                        for (String fc : fcs) {
                            customMap.put(fc, results.getObject(fc));
                        }
                        field.setCustomMap(customMap);
                    }
                    // ??????????????????
                    field.setName(columnName);
                    String newColumnName = columnName;
                    IKeyWordsHandler keyWordsHandler = dataSourceConfig.getKeyWordsHandler();
                    if (keyWordsHandler != null) {
                        if (keyWordsHandler.isKeyWords(columnName)) {
                            System.err.println(
                                    String.format("?????????[%s]????????????[%s]?????????????????????????????????!", tableName, columnName));
                            field.setKeyWords(true);
                            newColumnName = keyWordsHandler.formatColumn(columnName);
                        }
                    }
                    field.setColumnName(newColumnName);
                    field.setType(results.getString(dbQuery.fieldType()));
                    INameConvert nameConvert = strategyConfig.getNameConvert();
                    if (null != nameConvert) {
                        field.setPropertyName(nameConvert.propertyNameConvert(field));
                    } else {
                        field.setPropertyName(
                                strategyConfig, processName(field.getName(), config.getColumnNaming()));
                    }
                    field.setColumnType(
                            dataSourceConfig.getTypeConvert().processTypeConvert(globalConfig, field));
                    String fieldCommentColumn = dbQuery.fieldComment();
                    if (StringUtils.isNotBlank(fieldCommentColumn)) {
                        field.setComment(formatComment(results.getString(fieldCommentColumn)));
                    }
                    // ??????????????????
                    List<TableFill> tableFillList = getStrategyConfig().getTableFillList();
                    if (null != tableFillList) {
                        // ????????????????????????
                        tableFillList.stream()
                                .filter(tf -> tf.getFieldName().equalsIgnoreCase(field.getName()))
                                .findFirst()
                                .ifPresent(tf -> field.setFill(tf.getFieldFill().name()));
                    }
                    if (strategyConfig.includeSuperEntityColumns(field.getName())) {
                        // ??????????????????
                        commonFieldList.add(field);
                        continue;
                    }
                    fieldList.add(field);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception???" + e.getMessage());
        }
        tableInfo.setFields(fieldList);
        tableInfo.setCommonFields(commonFieldList);
        return tableInfo;
    }

    /**
     * ?????????????????????
     *
     * @param parentDir   ?????????????????????
     * @param packageName ??????
     * @return ??????????????????
     */
    private String joinPath(String parentDir, String packageName) {
        if (StringUtils.isBlank(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        return parentDir + packageName;
    }

    /**
     * ??????????????????
     *
     * @param parent     ?????????
     * @param subPackage ?????????
     * @return ??????????????????
     */
    private String joinPackage(String parent, String subPackage) {
        return StringUtils.isBlank(parent) ? subPackage : (parent + StringPool.DOT + subPackage);
    }

    /**
     * ??????????????????
     *
     * @return ????????????????????????????????????
     */
    private String processName(String name, NamingStrategy strategy) {
        return processName(name, strategy, strategyConfig.getFieldPrefix());
    }

    /**
     * ?????????/????????????
     *
     * @param name     ignore
     * @param strategy ignore
     * @param prefix   ignore
     * @return ????????????????????????????????????
     */
    private String processName(String name, NamingStrategy strategy, String[] prefix) {
        String propertyName;
        if (ArrayUtils.isNotEmpty(prefix)) {
            if (strategy == NamingStrategy.underline_to_camel) {
                // ?????????????????????????????????
                propertyName = NamingStrategy.removePrefixAndCamel(name, prefix);
            } else {
                // ????????????
                propertyName = NamingStrategy.removePrefix(name, prefix);
            }
        } else if (strategy == NamingStrategy.underline_to_camel) {
            // ??????????????????
            propertyName = NamingStrategy.underlineToCamel(name);
        } else {
            // ?????????
            propertyName = name;
        }
        return propertyName;
    }

    public StrategyConfig getStrategyConfig() {
        return strategyConfig;
    }

    public ConfigBuilder setStrategyConfig(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
        return this;
    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    public ConfigBuilder setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }

    public InjectionConfig getInjectionConfig() {
        return injectionConfig;
    }

    public ConfigBuilder setInjectionConfig(InjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
        return this;
    }

    /**
     * ??????????????????????????????
     *
     * @param comment ??????
     * @return ??????
     * @since 3.3.3
     */
    public String formatComment(String comment) {
        return StringUtils.isBlank(comment) ? StringPool.EMPTY : comment.replaceAll("\r\n", "\t");
    }
}
