package com.lc.springboot.mybatisplus.generatecode.extension;

import com.lc.springboot.mybatisplus.generatecode.config.ConstVal;
import com.lc.springboot.mybatisplus.generatecode.config.TemplateConfig;
import com.lc.springboot.mybatisplus.generatecode.config.builder.ConfigBuilder;
import com.lc.springboot.mybatisplus.generatecode.config.po.TableInfo;
import com.lc.springboot.mybatisplus.generatecode.engine.AbstractTemplateEngine;
import com.lc.springboot.mybatisplus.generatecode.extension.translate.youdao.YouDaoApiTranslate;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 表字段枚举收集器
 *
 * @author liangchao
 * @version 1.0
 * @date 2021/9/8 8:30 下午
 */
@Slf4j
@UtilityClass
public class EnumClassGenerateUtil {

    private static final String YES_NO = "1、是 2、否";

    private static final Set<String> generatedFileSet = new LinkedHashSet<>();

    public static void generateEnumsFile(TableInfo tableInfo,
                                         AbstractTemplateEngine templateEngine) {
        Map<String,EnumWrapper> result = new HashMap<>();


        // 获取配置信息
        Map<String, Object> objectMap = templateEngine.getObjectMap(tableInfo);
        ConfigBuilder configBuilder = templateEngine.getConfigBuilder();
        TemplateConfig template = configBuilder.getTemplate();
        Map<String, String> pathInfo = configBuilder.getPathInfo();

        tableInfo.getFields().forEach(tableField -> {
            // 数据库字段名
            String columnName = tableField.getColumnName();
            // 属性名称
            String propertyName = tableField.getPropertyName();
            // 获取注释
            String comment = tableField.getComment();
            if (StringUtils.isBlank(comment) || !comment.contains("|")) {
                return;
            }

            String enumsInfo = comment.split("\\|")[1].trim();
            // 转换成能解析的字符串形式
            enumsInfo = enumsInfo.replace("[:：-]", "、");
            enumsInfo = enumsInfo.replace("[\t\r\n]", " ");
            enumsInfo = enumsInfo.replace("  ", " ");

            if (generatedFileSet.contains(enumsInfo)) {
//                result.put(columnName,new EnumWrap  per());
                return;
            }

            if (!enumsInfo.contains("、")) {
                return;
            }

            Map<String, String> enumMap = str2Map(enumsInfo);

            if (enumMap.size() < 2) {
                return;
            }

            Map<String, String> translationMap = translateToEnMap(enumMap);

            generatedFileSet.add(enumsInfo);
            try {

                String enumClassName = tableInfo.getEntityName() + StringUtils.capitalize(tableField.getPropertyName()) + "Enum";
                objectMap.put("enumsMap", enumMap);
                objectMap.put("translationMap", translationMap);
                objectMap.put("enumCurrentTableField", tableField);
                objectMap.put("enumCurrentFieldComment", comment.split("\\|")[0].trim());
                objectMap.put("enumCurrentTable", tableInfo);
                objectMap.put("enumClassName", enumClassName);

                if (YES_NO.equals(enumsInfo)) {
                    enumClassName = "YesNoEnum";
                    objectMap.put("enumClassName", enumClassName);
                }

                String enumsFiles =
                        String.format(
                                (pathInfo.get(ConstVal.ENUMS_PATH)
                                        + File.separator
                                        + enumClassName
                                        + templateEngine.suffixJavaOrKt()),
                                configBuilder.getPackageInfo().get(ConstVal.MODULE_NAME));

                templateEngine.writerFile(objectMap, templateEngine.templateFilePath(template.getEnums()), enumsFiles);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }

    private static Map<String, String> translateToEnMap(Map<String, String> enumMap) {
//        FileUtil.appendUtf8String(enumMap.toString() + "\n", new File("/Users/liangchao/tmp/aa.txt"));
        Map<String, String> translationMap = new HashMap<>();
        enumMap.forEach((k, v) -> {
            try {
                String replace = v.replace("/", " ");
                replace = replace.replace("[,，]", " ");
                String translateInfo = YouDaoApiTranslate.translate("zh", "en", replace);
                log.info("翻译信息：" + v + "/" + translateInfo);

                // 休眠一段时间，防止被拦截
                TimeUnit.MICROSECONDS.sleep(300);

                if (translationMap.containsValue(translateInfo)) {
                    translateInfo = translateInfo + "_2";
                }
                translationMap.put(k, spaceToUnderLine(translateInfo.toUpperCase()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return translationMap;
    }

    /**
     * string转map
     */
    private static Map<String, String> str2Map(String enumsInfo) {
        Map<String, String> result = new LinkedHashMap<>();
        String[] enumArray = enumsInfo.split(" ");
        for (String enumInfo : enumArray) {
            String[] split = enumInfo.split("、");
            if (split.length < 2) {
                log.error("检测到枚举字段注释不规范：{}", enumsInfo);
                return result;
            }
            result.put(split[0].trim(), split[1].trim());
        }
        return result;
    }

    private static String spaceToUnderLine(String translationInfo) {
        // 如果碰到一些特殊的单词直接过滤掉,例如[a, an, of, the]
        return Arrays.stream(translationInfo.split(" "))
                .filter(a -> !a.equalsIgnoreCase("a"))
                .filter(a -> !a.equalsIgnoreCase("an"))
                .filter(a -> !a.equalsIgnoreCase("of"))
                .filter(a -> !a.equalsIgnoreCase("the"))
                .collect(Collectors.joining("_"));

//        return translationInfo.replace(" ", "_");
//        return com.baomidou.mybatisplus.core.toolkit.StringUtils.underlineToCamel(replace);
    }


    public static void main(String[] args) {
        System.out.println(spaceToUnderLine("a flower of get"));
    }

}
