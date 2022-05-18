package com.gw.springboot.gencode.test; // package com.chinamobile.zj.idc.management.config;

import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.gw.springboot.mybatisplus.generatecode.AutoGenerator;
import com.gw.springboot.mybatisplus.generatecode.config.DataSourceConfig;
import com.gw.springboot.mybatisplus.generatecode.config.GlobalConfig;
import com.gw.springboot.mybatisplus.generatecode.config.PackageConfig;
import com.gw.springboot.mybatisplus.generatecode.config.StrategyConfig;
import com.gw.springboot.mybatisplus.generatecode.config.TemplateConfig;
import com.gw.springboot.mybatisplus.generatecode.config.converts.MySqlTypeConvert;
import com.gw.springboot.mybatisplus.generatecode.config.po.LikeTable;
import com.gw.springboot.mybatisplus.generatecode.config.rules.DateType;
import com.gw.springboot.mybatisplus.generatecode.config.rules.DbColumnType;
import com.gw.springboot.mybatisplus.generatecode.config.rules.NamingStrategy;
import com.gw.springboot.mybatisplus.generatecode.engine.FreemarkerTemplateEngine;

/**
 * mybatisplus3代码生成器,多租户模式下
 *
 * @author gewei
 */
public class TanentMycatTestCodeGenerator {

    public static void main(String[] args) {

        // 是否覆盖原来的文件
        boolean fileOverride = true;

        // 系统版本(用到注释中)
        String version = "1.0";
        // 项目名称
        String projectName = "mycat-sample-02";
        // 定义url路径前缀
        String rootUrlMappering = "mycat";
        // 模板名称
        String moduleName = "";
        // 顶层包名
        String baseParentPkg = "com.gewei.learning";
        // 根包名
        String basePackage = baseParentPkg + "." + rootUrlMappering;
        // 开发人员
        String author = "gewei";

        //是否生成feign client代码
        boolean enableFeignClient = false;
        // 设置程序代码输出根目录
//        String outputRootDir = "/Users/gewei/tmp2/";
        String outputRootDir = "/Users/gewei/IdeaProjects/qjd/learning/mycat-samples/";
        // 设置代码输出目录
        String outputDir = outputRootDir + projectName + "/src/main/java/";
        // 设置mapperXml文件的输出目录
        String xmlOutputDir = outputRootDir + projectName + "/src/main/resources/"; //

        // 模板文件路径
        String templateDir = "normal-common";

        // 数据库配置---------------------
//        String dbDriverName = "com.mysql.cj.jdbc.Driver";
        String dbDriverName = "com.mysql.jdbc.Driver";

        //本地测试
        String dbUserName = "root";
        String dbPassword = "123456";
        String dbUrl = "jdbc:mysql://localhost:3307/tanent_db_default";

        // 表前缀
        String tablePrefix = "";

        //String[] tables = new String[]{""};

        String[] tables = new String[]{};
        String likeTable = "";

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setDateType(DateType.ONLY_DATE);
        gc.setProjectName(rootUrlMappering);
        // 设置代码输出目录[java]
        gc.setOutputDir(outputDir);
        // 设置mapper xml文件输出目录
        gc.setXmlOutputDir(xmlOutputDir);
        // 开发人员
        gc.setAuthor(author);
        // 系统版本
        gc.setVersion(version);
        // controller URL 根路径
        gc.setControllerRootMapPath(String.format("/v1/%s/", rootUrlMappering));
        // 是否开启swagger2
        gc.setSwagger2(true);
        // 是否打开输出目录
        gc.setOpen(true);

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

        // 关闭 ActiveRecord 模式
        gc.setActiveRecord(false);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);

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
        // 顶层包名
        pc.setBaseParentPkg(baseParentPkg);
        // 父包名
        pc.setParent(basePackage);
        // 父包模块名
        pc.setModuleName(moduleName);
        // 定义实体类包名
        pc.setEntity("model");
        // 定义mapper类包名
        pc.setMapper("mapper");
        // 定义xml包名
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

        mpg.setTemplate(tc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 忽略生成service接口层
        strategy.setIgnoreInterfaceSericeGen(false);
        // 是否生成字段常量
        strategy.setEntityColumnConstant(true);
        // 数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(
                // 数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
                NamingStrategy.underline_to_camel);
        // 自定义继承的Entity类全称，带包名
//        strategy.setSuperEntityClass("com.qjdchina.saas.common.web.BaseModel");
        // 自定义基础的Entity类，公共字段
//        strategy.setSuperEntityColumns(
//                "id",
//                "created_by",
//                "created_time",
//                "updated_time",
//                "delete_flag");

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
//        strategy.setExclude("undo_log");
        strategy.setLikeTable(new LikeTable(likeTable, SqlLike.RIGHT));
        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);

        mpg.setStrategy(strategy);

        // 设置模板引擎
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        // 执行结果
        mpg.execute();
    }
}
