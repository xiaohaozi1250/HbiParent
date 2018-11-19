package ${package}.controllers;

<#list import as e>
import ${e};
</#list>
import ${package}.dto.${lineDtoName};
import ${package}.service.${lineServiceName};
import ${package}.dto.${headerDtoName};
import ${package}.service.${headerServiceName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

@Controller
public class ${headerControllerName} extends BaseController {
    //test
    @Autowired
    private ${headerServiceName} service;

    @Autowired
    private ${lineServiceName} serviceLine;

    @RequestMapping(value = "${headerQueryUrl}")
    @ResponseBody
    public ResponseData query(${headerDtoName} dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "${headerSubmitUrl}")
    @ResponseBody
    public ResponseData update(@RequestBody List<${headerDtoName}> dto, BindingResult result, HttpServletRequest request) {
        this.getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        } else {
            IRequest requestCtx = this.createRequestContext(request);
            return new ResponseData(this.service.batchUpdate(requestCtx, dto));
        }
    }

    @RequestMapping(value = "${headerRemoveUrl}")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<${headerDtoName}> dto) {
        this.service.batchDeleteHeaders(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "${lineQueryUrl}")
    @ResponseBody
    public ResponseData queryLine(${lineDtoName} dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(serviceLine.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "${lineSubmitUrl}")
    @ResponseBody
    public ResponseData updateLine(@RequestBody List<${lineDtoName}> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(serviceLine.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "${lineRemoveUrl}")
    @ResponseBody
    public ResponseData deleteLine(HttpServletRequest request, @RequestBody List<${lineDtoName}> dto) {
        serviceLine.batchDelete(dto);
        return new ResponseData();
    }
}