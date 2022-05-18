<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

    <#if enableCache>
        <!-- 开启二级缓存 -->
        <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>
    </#if>
    <#if baseResultMap>
        <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
            <#list table.fields as field>
                <#if field.keyFlag><#--生成主键排在第一位-->
                    <id column="${field.name}" property="${field.propertyName}"/>
                </#if>
            </#list>
            <#list table.commonFields as field><#--生成公共字段 -->
                <result column="${field.name}" property="${field.propertyName}"/>
            </#list>
            <#list table.fields as field>
                <#if !field.keyFlag><#--生成普通字段 -->
                <result column="${field.name}" property="${field.propertyName}"/>
                </#if>
            </#list>
        </resultMap>
    </#if>

    <delete id="physicallyDelete">
        delete
        from ${table.name} where 1 = 1
        <if test="idList != null and idList.size() != 0">
            and id in
            <foreach collection="idList" item="item" index="index" open="(" close=")" separator=",">
                <#noparse>#{item}</#noparse>
            </foreach>
        </if>
        <if test="idList == null or idList.size() == 0">
            and 1 = 2
        </if>
    </delete>
</mapper>
