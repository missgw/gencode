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
package com.gw.springboot.mybatisplus.generatecode.config.converts;


import com.gw.springboot.mybatisplus.generatecode.config.ITypeConvert;
import com.gw.springboot.mybatisplus.generatecode.config.GlobalConfig;
import com.gw.springboot.mybatisplus.generatecode.config.rules.DbColumnType;
import com.gw.springboot.mybatisplus.generatecode.config.rules.IColumnType;

import static com.gw.springboot.mybatisplus.generatecode.config.rules.DbColumnType.*;

/**
 * PostgreSQL 字段类型转换
 *
 * @author hubin, hanchunlin
 * @since 2017-01-20
 */
public class PostgreSqlTypeConvert implements ITypeConvert {
    public static final PostgreSqlTypeConvert INSTANCE = new PostgreSqlTypeConvert();

    /**
     * @inheritDoc
     */
    @Override
    public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
        return TypeConverts.use(fieldType)
            .test(TypeConverts.containsAny("char", "text", "json", "enum").then(STRING))
            .test(TypeConverts.contains("bigint").then(LONG))
            .test(TypeConverts.contains("int").then(INTEGER))
            .test(TypeConverts.containsAny("date", "time").then(t -> toDateType(config, t)))
            .test(TypeConverts.contains("bit").then(BOOLEAN))
            .test(TypeConverts.containsAny("decimal", "numeric").then(BIG_DECIMAL))
            .test(TypeConverts.contains("clob").then(CLOB))
            .test(TypeConverts.contains("blob").then(BYTE_ARRAY))
            .test(TypeConverts.contains("float").then(FLOAT))
            .test(TypeConverts.contains("double").then(DOUBLE))
            .test(TypeConverts.contains("boolean").then(BOOLEAN))
            .or(STRING);
    }

    /**
     * 转换为日期类型
     *
     * @param config 配置信息
     * @param type   类型
     * @return 返回对应的列类型
     */
    public static IColumnType toDateType(GlobalConfig config, String type) {
        switch (config.getDateType()) {
            case SQL_PACK:
                switch (type) {
                    case "date":
                        return DbColumnType.DATE_SQL;
                    case "time":
                        return DbColumnType.TIME;
                    default:
                        return DbColumnType.TIMESTAMP;
                }
            case TIME_PACK:
                switch (type) {
                    case "date":
                        return DbColumnType.LOCAL_DATE;
                    case "time":
                        return DbColumnType.LOCAL_TIME;
                    default:
                        return DbColumnType.LOCAL_DATE_TIME;
                }
            default:
                return DbColumnType.DATE;
        }
    }
}
