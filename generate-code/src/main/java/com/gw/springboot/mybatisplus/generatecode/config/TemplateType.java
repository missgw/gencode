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

/**
 * 模板类型
 *
 * @author nieqiurong 2020/4/28.
 * @since 3.3.2
 */
public enum TemplateType {
  ENTITY,
  SERVICE,
  CONTROLLER,
  MAPPER,
  XML,

  //self add
  DTO_ADD,
  DTO_QUERY,
  DTO_UPDATE,
  DTO_DETAIL_REQUEST,
  DTO_DETAIL_RESPONSE,
  DTO_DELETE_REQUEST,
  FEIGN_CLIENT,
  FEIGN_CLIENT_CONST,
  ENUMS;
}