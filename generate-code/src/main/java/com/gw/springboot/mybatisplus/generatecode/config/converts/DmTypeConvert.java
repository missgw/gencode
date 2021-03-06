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
import com.gw.springboot.mybatisplus.generatecode.config.rules.IColumnType;

import static com.gw.springboot.mybatisplus.generatecode.config.rules.DbColumnType.*;

/**
 * DM 字段类型转换
 *
 * @author halower, hanchunlin
 * @since 2019-06-27
 */
public class DmTypeConvert implements ITypeConvert {
    public static final DmTypeConvert INSTANCE = new DmTypeConvert();

    /**
     * 字符数据类型: CHAR,CHARACTER,VARCHAR
     * <p>
     * 数值数据类型: NUMERIC,DECIMAL,DEC,MONEY,BIT,BOOL,BOOLEAN,INTEGER,INT,BIGINT,TINYINT,BYTE,SMALLINT,BINARY,
     * VARBINARY
     * <p>
     * 近似数值数据类型: FLOAT
     * <p>
     * DOUBLE, DOUBLE PRECISION,REAL
     * <p>
     * 日期时间数据类型
     * <p>
     * 多媒体数据类型: TEXT,LONGVARCHAR,CLOB,BLOB,IMAGE
     *
     * @param config    全局配置
     * @param fieldType 字段类型
     * @return 对应的数据类型
     * @inheritDoc
     */
    @Override
    public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
        return TypeConverts.use(fieldType)
            .test(TypeConverts.containsAny("char", "text").then(STRING))
            .test(TypeConverts.containsAny("numeric", "dec", "money").then(BIG_DECIMAL))
            .test(TypeConverts.containsAny("bit", "bool").then(BOOLEAN))
            .test(TypeConverts.contains("bigint").then(BIG_INTEGER))
            .test(TypeConverts.containsAny("int", "byte").then(INTEGER))
            .test(TypeConverts.contains("binary").then(BYTE_ARRAY))
            .test(TypeConverts.contains("float").then(FLOAT))
            .test(TypeConverts.containsAny("double", "real").then(DOUBLE))
            .test(TypeConverts.containsAny("date", "time").then(DATE))
            .test(TypeConverts.contains("clob").then(CLOB))
            .test(TypeConverts.contains("blob").then(BLOB))
            .test(TypeConverts.contains("image").then(BYTE_ARRAY))
            .or(STRING);
    }

}
