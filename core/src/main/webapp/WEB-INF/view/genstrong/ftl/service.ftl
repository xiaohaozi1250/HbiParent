package ${package}.service;

<#list import as e>
import ${e};
</#list>
<#if source=="header">
import ${package}.dto.${headerDtoName};
import com.hand.hap.core.IRequest;
import java.util.List;

public interface ${headerServiceName} extends IBaseService<${headerDtoName}>, ProxySelf<${headerServiceName}> {

    boolean batchDeleteHeaders(List<${headerDtoName}> headers);

    List<${headerDtoName}> batchUpdate(IRequest request, List<${headerDtoName}> headers);

    ${headerDtoName} create${headerDtoName}(${headerDtoName} header);

    ${headerDtoName} update${headerDtoName}(${headerDtoName} header);
}
<#else>
import ${package}.dto.${lineDtoName};

public interface ${lineServiceName} extends IBaseService<${lineDtoName}>, ProxySelf<${lineServiceName}> {

}
</#if>