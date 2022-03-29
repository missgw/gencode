package com.lc.springboot.gencode.saas; // package com.chinamobile.zj.idc.management.config;

import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.lc.springboot.mybatisplus.generatecode.AutoGenerator;
import com.lc.springboot.mybatisplus.generatecode.config.*;
import com.lc.springboot.mybatisplus.generatecode.config.converts.MySqlTypeConvert;
import com.lc.springboot.mybatisplus.generatecode.config.po.LikeTable;
import com.lc.springboot.mybatisplus.generatecode.config.rules.DateType;
import com.lc.springboot.mybatisplus.generatecode.config.rules.DbColumnType;
import com.lc.springboot.mybatisplus.generatecode.config.rules.NamingStrategy;
import com.lc.springboot.mybatisplus.generatecode.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liangchao
 * @version 1.0
 * @date 2021/6/1 10:38 上午
 */

public abstract class AbstractSaasBaseGenCode {

    // 是否覆盖原来的文件
    protected boolean fileOverride = true;

    public DB_ENV db_env = DB_ENV.LOCAL;

    // 根包名
    public String rootPackageName = "com.qjdchina.saas.";
    // 基础实体类
    public String superEntityClassName = "com.qjdchina.saas.common.web.BossBaseModel";
    // mapper基类
    public String superMapperClass = ConstVal.SUPER_MAPPER_CLASS;
    // 基础字段
    public String[] commonFieldsArray = new String[]{"id",
            "org_id",
            "created_by",
            "created_time",
            "updated_time",
            "delete_flag"};

    // 数据库配置-本地环境
//    protected String dbDriverName = "com.mysql.jdbc.Driver";
    protected String dbDriverName = "com.mysql.cj.jdbc.Driver";
    public String dbUserName = "root";
    public String dbPassword = "lc123456";
    public String dbUrl = "jdbc:mysql://localhost:3306/saas_user1";

    // 表前缀
    public String tablePrefix = "sa";
    public String likeTable = "sa_";

    // 是否生成enum枚举类信息
    public boolean enableEnum = false;

    // 需要生成的表数组
    //String[] tables = new String[]{}; // 全部生成，慎用！！！！！
    public String[] tables = new String[]{"测试表"};

    // 项目名称
    public String projectName = "项目名称";

    //url规则
    public String getRootUrlMappingPattern = "/v1/%s/";
    // 定义url路径前缀
    public String rootUrlMapping = "定义url路径前缀";
    // 定义代码生成根路径
    public String outputRootDir = "/Users/liangchao/tmp/qjd-saas/工程名称";
    /**
     * dto代码生成根路径
     * 用于生成dto代码，用于封装dubbo service api
     */
    public String dtoOutputRootDir = "/Users/liangchao/tmp/qjd-saas/saas-api/api工程/";
    // 模板文件路径
    public String templateDir = "qjd-saas-boss-dubbo-2";
    // 开发人员
    protected String author = "liangchao";

    public void genCode() {
        // 模板名称
        String moduleName = "";

        if (db_env == DB_ENV.DEV) {
            // 开发环境 数据库配置---------------------
            dbDriverName = "com.mysql.cj.jdbc.Driver";
            dbUserName = "root";
            dbPassword = "qjd123";
            dbUrl = "jdbc:mysql://10.1.1.42:3306/saas_user1";
        } else if (db_env == DB_ENV.TEST) {
            // 测试环境
            dbDriverName = "com.mysql.cj.jdbc.Driver";
            dbUserName = "admin";
            dbPassword = "I6PC8Gq4EW0N7Em8";
            dbUrl = "jdbc:mysql://10.1.1.99:3306/saas_user1";
        }else if (db_env == DB_ENV.TEST_A) {
            // 测试-A 环境
            dbDriverName = "com.mysql.cj.jdbc.Driver";
            dbUrl = "jdbc:mysql://10.7.1.12:61306/saas_default?characterEncoding=utf8&useUnicode=true&useSSL=false&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&serverTimezone=GMT%2B8";
            dbUserName = "qjd_dev_user";
            dbPassword = "28d8l6$a";
        }

        // 系统版本(用到注释中)
        String version = "1.0";
        // 根包名
        String basePackage = rootPackageName + rootUrlMapping;


        // 是否生成feign client代码
        boolean enableFeignClient = false;
        // 设置代码输出目录
        String outputDir = outputRootDir + projectName + "/src/main/java/";
        String dtoOutputDir = dtoOutputRootDir + "src/main/java/";

        // 设置mapperXml文件的输出目录
        String xmlOutputDir = outputRootDir + projectName + "/src/main/resources/"; //

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setDateType(DateType.ONLY_DATE);
        gc.setProjectName(rootUrlMapping);
        // 设置代码输出目录[java]
        gc.setOutputDir(outputDir);
        // 设置dto代码输出目录[java]
        gc.setDtoOutputDir(dtoOutputDir);
        // 设置mapper xml文件输出目录
        gc.setXmlOutputDir(xmlOutputDir);
        // 开发人员
        gc.setAuthor(author);
        // 系统版本
        gc.setVersion(version);
        // controller URL 根路径
        if (StringUtils.isNotBlank(getRootUrlMappingPattern)) {
            gc.setControllerRootMapPath(String.format(getRootUrlMappingPattern, rootUrlMapping));
        } else {
            gc.setControllerRootMapPath("/" + rootUrlMapping + "/");
        }
        // 是否开启swagger2
        gc.setSwagger2(true);
        // 是否打开输出目录
        gc.setOpen(true);
        // 设置是否生成feign client客户端代码

        // service 命名方式,次生成模式不用Service接口类
        gc.setServiceName("%sService");

        // serviceImpl 命名方式
        gc.setServiceImplName("%sServiceImpl");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        // mapper xml 命名方式
        gc.setXmlName("%sMapper");

        // 是否覆盖
        gc.setFileOverride(fileOverride);
        // 是否生成枚举类
        gc.setEnableEnum(enableEnum);

        // 关闭 ActiveRecord 模式
        gc.setActiveRecord(false);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(false);
        // XML columList
        gc.setBaseColumnList(false);

        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDriverName(dbDriverName);
        dsc.setUsername(dbUserName);
        dsc.setPassword(dbPassword);
        dsc.setUrl(dbUrl);
        dsc.setTypeConvert(
                new MySqlTypeConvert() {
                    @Override
                    public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        // 将数据库中datetime转换成date
                        if (fieldType.toLowerCase().contains("datetime")) {
                            return DbColumnType.DATE;
                        }
                        return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
                    }
                });
        mpg.setDataSource(dsc);

        // ==============================================包配置=================================================
        PackageConfig pc = new PackageConfig();
        // 父包名
        pc.setParent(basePackage);
        // 父包模块名
        pc.setModuleName(moduleName);
        // 定义实体类包名
        pc.setEntity("model");
        // 定义mapper类包名
        pc.setMapper("mapper");
        // 定义xml包名
        // pc.setXml("mapper.xml");
        pc.setXml("mapper");

        // 定义service包名
        pc.setService("service");

        // 定义service实现类包名
        pc.setServiceImpl("service.impl");
        // 定义controller包名
        pc.setController("controller");
        // 定义dto请求包名
        pc.setDtoRequest("dto.request");
        // 定义dto响应包名
        pc.setDtoResponse("dto.response");
        // 定义enums包名
        pc.setEnums("enums");

        mpg.setPackageInfo(pc);

        TemplateConfig tc = new TemplateConfig();
        // 设置controller模板文件
        tc.setController(String.format("/templates/%s/controller.java", templateDir));
        // 设置model类模板文件
        tc.setEntity(String.format("/templates/%s/entity.java", templateDir));
        // 设置service实现类模板文件
        tc.setService(String.format("/templates/%s/service.java", templateDir));
        // 设置service实现类模板文件
        tc.setServiceImpl(String.format("/templates/%s/serviceImpl.java", templateDir));
        // 设置mapper类接口模板文件
        tc.setMapper(String.format("/templates/%s/mapper.java", templateDir));
        // 设置mapper xml文档模板文件
        tc.setXml(String.format("/templates/%s/mapper.xml", templateDir));
        // 设置dto add类模板文件
        tc.setDtoAdd(String.format("/templates/%s/dtoAdd.java", templateDir));
        // 设置dto query类模板文件
        tc.setDtoQuery(String.format("/templates/%s/dtoQuery.java", templateDir));
        // 设置dto update类模板文件
        tc.setDtoUpdate(String.format("/templates/%s/dtoUpdate.java", templateDir));
        // 设置dto detail request类模板文件
        tc.setDtoDetailRequest(String.format("/templates/%s/dtoDetailQueryRequest.java", templateDir));
        // 设置dto detail response类模板文件
        tc.setDtoDetailResponse(String.format("/templates/%s/dtoDetailResponse.java", templateDir));
        // 设置dto delete request类模板文件
        tc.setDtoDeleteRequest(String.format("/templates/%s/dtoDeleteRequest.java", templateDir));
        // 设置enums类模板文件
        tc.setEnums(String.format("/templates/%s/enums.java", templateDir));

        mpg.setTemplate(tc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 忽略生成service接口层
        strategy.setIgnoreInterfaceSericeGen(false);
        // 是否生成字段常量
        strategy.setEntityColumnConstant(false);
        // 数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 自定义继承的Entity类全称，带包名
        strategy.setSuperEntityClass(superEntityClassName);
        // 自定义基础的Entity类，公共字段
        strategy.setSuperEntityColumns(commonFieldsArray);
        // 自定义mapper基类
        strategy.setSuperMapperClass(superMapperClass);

        // 【实体】是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(true);
        // 生成 @RestController 控制器
        strategy.setRestControllerStyle(true);
        // 自定义继承的Controller类全称，带包名
        // strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");

        // 表前缀
        strategy.setTablePrefix(tablePrefix);
        // 需要包含的表名，允许正则表达式
        strategy.setInclude(tables);

        // seata的日志表需要排除
        if (tables.length == 0) {
            strategy.setExclude("undo_log",
                    "credit_control",
                    "credit_invalid_log",
                    "credit_limit",
                    "credit_limit_history",
                    "credit_log",
                    "cr_field_rule");
        }

        strategy.setLikeTable(new LikeTable(likeTable, SqlLike.RIGHT));
        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);

        mpg.setStrategy(strategy);

        // 设置模板引擎
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        // 执行结果
        mpg.execute();
    }

    public enum DB_ENV {
        LOCAL,
        DEV,
        TEST,
        TEST_A
    }
}
