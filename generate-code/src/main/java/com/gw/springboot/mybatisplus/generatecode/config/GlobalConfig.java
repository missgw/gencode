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
package com.gw.springboot.mybatisplus.generatecode.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.gw.springboot.mybatisplus.generatecode.config.rules.DateType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 全局配置
 *
 * @author hubin
 * @since 2016-12-02
 */
@Data
@Accessors(chain = true)
public class GlobalConfig {

  /** 生成文件的输出目录【默认 D 盘根目录】[java代码] */
  private String outputDir = "D://";

  /**
   * dto 生成的目录
   */
  private String dtoOutputDir = "D://";

  /** 生成xml文件的输出目录 self add */
  private String xmlOutputDir = "D://";

  /** 是否覆盖已有文件 */
  private boolean fileOverride = false;

  /** 是否打开输出目录 */
  private boolean open = true;

  /** 是否在xml中添加二级缓存配置 */
  private boolean enableCache = false;

  /** 开发人员 */
  private String author;

  /** 开启 Kotlin 模式 */
  private boolean kotlin = false;

  /** 开启 swagger2 模式 */
  private boolean swagger2 = false;

  /** 开启 ActiveRecord 模式 */
  private boolean activeRecord = false;

  /** 开启 BaseResultMap */
  private boolean baseResultMap = false;

  /** 时间类型对应策略 */
  private DateType dateType = DateType.TIME_PACK;

  /** 开启 baseColumnList */
  private boolean baseColumnList = false;
  /** 各层文件名称方式，例如： %sAction 生成 UserAction %s 为占位符 */
  private String entityName;

  private String mapperName;
  private String xmlName;
  private String serviceName;
  private String serviceImplName;
  private String controllerName;

  // self add
  private String dtoAddName;
  private String dtoQueryName;
  private String dtoUpdateName;
  private String dtoDetailRequestName;
  private String dtoDetailResponseName;
  private String dtoDeleteRequestName;
  //openfeign
  private String feignClientName;
  private String feignClientConstName;
  //enums
  private String enumsName;

  //self add
  /**
   * 项目名称
   */
  private String projectName;

  // self add
  /** 系统版本 */
  private String version;

  // self add
  /** 模块controllerURL映射根路径,例如(/v1/xxx/) */
  private String controllerRootMapPath;

  // self add
  /** 是否开启FeignClient，如果开启会生成feign客户端的Client类 */
  private boolean enableFeignClient;
  /** feign客户端对应的url，值是占位符，用于客户端可以在配置文件properties或者yml等自主配置 */
  private String feignClientEndpoint;

  // 是否开启enum模板生成
  private boolean enableEnum;

  /** 指定生成的主键的ID类型 */
  private IdType idType;
}
