package ${package}.service.impl;

<#list import as e>
import ${e};
</#list>
import org.springframework.transaction.annotation.Transactional;
<#if source=="header">
import ${package}.dto.${headerDtoName};
import ${package}.service.${headerServiceName};
import ${package}.mapper.${headerMapperName};
import ${package}.dto.${lineDtoName};
import ${package}.mapper.${lineMapperName};
import com.hand.hap.core.IRequest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ${headerImplName} extends BaseServiceImpl<${headerDtoName}> implements ${headerServiceName} {

    @Autowired
    private ${headerMapperName} headerMapper;
    @Autowired
    private ${lineMapperName} lineMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteHeaders(List<${headerDtoName}> headers) {
        Iterator var2 = headers.iterator();

        while (var2.hasNext()) {
            ${headerDtoName} header = (${headerDtoName}) var2.next();
            headerMapper.deleteByPrimaryKey(header);
            this.lineMapper.deleteBy${columnsInfoHeader[0].tableColumnsName?cap_first}(header.get${columnsInfoHeader[0].tableColumnsName?cap_first}());
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<${headerDtoName}> batchUpdate(IRequest request, List<${headerDtoName}> headers) {
        for (${headerDtoName} header : headers) {
            if (header.get${columnsInfoHeader[0].tableColumnsName?cap_first}() == null) {
                self().create<${headerDtoName}>(header);
            } else if (header.get${columnsInfoHeader[0].tableColumnsName?cap_first}() != null) {
                self().update<${headerDtoName}>(header);
            }
        }
        return headers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${headerDtoName} create${headerDtoName}(${headerDtoName} header) {
        headerMapper.insertSelective(header);
        if (header.getLines() != null) {
            for (${lineDtoName} line : header.getLines()) {
                line.set${columnsInfoHeader[0].tableColumnsName?cap_first}(header.get${columnsInfoHeader[0].tableColumnsName?cap_first}());
                lineMapper.insertSelective(line);
            }
        }
        return header;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${headerDtoName} update${headerDtoName}(${headerDtoName} header) {
        int updateCount = headerMapper.updateByPrimaryKeySelective(header);
        checkOvn(updateCount, header);
        if (header.getLines() != null) {
            for (${lineDtoName} line : header.getLines()) {
                if (line.get${columnsInfoLine[0].tableColumnsName?cap_first}() == null) {
                    line.set${columnsInfoHeader[0].tableColumnsName?cap_first}(header.get${columnsInfoHeader[0].tableColumnsName?cap_first}());
                    lineMapper.insertSelective(line);
                } else {
                    lineMapper.updateByPrimaryKeySelective(line);
                }
            }
        }
        return header;
    }
}
<#else>
import ${package}.dto.${lineDtoName};
import ${package}.service.${lineServiceName};

@Service
@Transactional(rollbackFor = Exception.class)
public class ${lineImplName} extends BaseServiceImpl<${lineDtoName}> implements ${lineServiceName} {

}
</#if>