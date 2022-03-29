package com.lc.springboot.gencode.sqlrepire;


import cn.hutool.core.io.FileUtil;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 针对mac的某种设计工具需要做建表语句转换
 *
 * @author liangchao
 * @version 1.0
 * @date 2021/8/30 2:44 下午
 */

public class SaasSqlRepair {

    public static void main(String[] args) throws IOException {
        String filePath = "/Users/liangchao/公司/仟金顶/项目/Saas平台/数据模型/saas-ddl.sql";
        List<String> readLines = FileUtil.readLines(filePath, StandardCharsets.UTF_8);

        List<String> result = new ArrayList<>(readLines.size());

        for (String readLine : readLines) {
            if (readLine.startsWith(") COMMENT")) {
                readLine = readLine.replace(") COMMENT", ") ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT");
                readLine = readLine.replace(" '", "'");
            }
            result.add(readLine);
        }

        FileUtil.writeLines(result, FileUtil.file(filePath), StandardCharsets.UTF_8);

        Desktop.getDesktop().open(FileUtil.file(filePath));
    }
}
