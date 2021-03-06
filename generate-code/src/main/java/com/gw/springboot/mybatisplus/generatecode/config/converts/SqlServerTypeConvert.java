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
 * SQLServer 字段类型转换
 *
 * @author hubin, hanchunlin
 * @since 2017-01-20
 */
public class SqlServerTypeConvert implements ITypeConvert {
    public static final SqlServerTypeConvert INSTANCE = new SqlServerTypeConvert();

    /**
     * @inheritDoc
     */
    @Override
    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        return TypeConverts.use(fieldType)
            .test(TypeConverts.containsAny("char", "xml", "text").then(STRING))
            .test(TypeConverts.contains("bigint").then(LONG))
            .test(TypeConverts.contains("int").then(INTEGER))
            .test(TypeConverts.containsAny("date", "time").then(STRING))
            .test(TypeConverts.contains("bit").then(BOOLEAN))
            .test(TypeConverts.containsAny("decimal", "numeric").then(DOUBLE))
            .test(TypeConverts.contains("money").then(BIG_DECIMAL))
            .test(TypeConverts.containsAny("binary", "image").then(BYTE_ARRAY))
            .test(TypeConverts.containsAny("float", "real").then(FLOAT))
            .or(STRING);
    }

}
