package ${package}.mapper;

<#list import as e>
import ${e};
</#list>
<#if source=="header">
import ${package}.dto.${headerDtoName};
public interface ${headerMapperName} extends Mapper<${headerDtoName}>{
}
<#else>
import ${package}.dto.${lineDtoName};
public interface ${lineMapperName} extends Mapper<${lineDtoName}>{
    int deleteBy${columnsInfoHeader[0].tableColumnsName?cap_first}(Long var1);
}
</#if>
