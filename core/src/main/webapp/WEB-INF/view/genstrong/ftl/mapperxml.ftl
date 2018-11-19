<?xml version="1.0" encoding="UTF-8" ?>

<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<#if source=="header">
<mapper namespace="${package}.mapper.${headerMapperXmlName}">
    <resultMap id="BaseResultMap" type="${package}.dto.${headerDtoName}">
        <#list columnsInfoHeader as infos>
            <result column="${infos.dBColumnsName!""}" property="${infos.tableColumnsName}"
                    jdbcType="${infos.jdbcType}"/>
        </#list>
    </resultMap>
<#else>
<mapper namespace="${package}.mapper.${lineMapperXmlName}">
    <resultMap id="BaseResultMap" type="${package}.dto.${lineDtoName}">
        <#list columnsInfoLine as infos>
            <result column="${infos.dBColumnsName!""}" property="${infos.tableColumnsName}"
                    jdbcType="${infos.jdbcType}"/>
        </#list>
    </resultMap>
    <delete id="deleteBy${columnsInfoHeader[0].tableColumnsName?cap_first}" parameterType="java.lang.Long">
        DELETE FROM ${tableName}
        WHERE ${columnsInfoHeader[0].dBColumnsName!""}= ${'#'}{${columnsInfoHeader[0].dBColumnsName!""},jdbcType=${columnsInfoHeader[0].jdbcType}}
    </delete>
</#if>
</mapper>