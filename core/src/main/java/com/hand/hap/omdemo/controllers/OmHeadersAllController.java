package com.hand.hap.omdemo.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.omdemo.dto.OmLinesAll;
import com.hand.hap.omdemo.service.IOmLinesAllService;
import com.hand.hap.omdemo.dto.OmHeadersAll;
import com.hand.hap.omdemo.service.IOmHeadersAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

@Controller
public class OmHeadersAllController extends BaseController {
    //test
    @Autowired
    private IOmHeadersAllService service;

    @Autowired
    private IOmLinesAllService serviceLine;

    @RequestMapping(value = "/cux/om/headers/all/query")
    @ResponseBody
    public ResponseData query(OmHeadersAll dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/cux/om/headers/all/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<OmHeadersAll> dto, BindingResult result, HttpServletRequest request) {
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

    @RequestMapping(value = "/cux/om/headers/all/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<OmHeadersAll> dto) {
        this.service.batchDeleteHeaders(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/cux/om/lines/all/query")
    @ResponseBody
    public ResponseData queryLine(OmLinesAll dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(serviceLine.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/cux/om/lines/all/submit")
    @ResponseBody
    public ResponseData updateLine(@RequestBody List<OmLinesAll> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(serviceLine.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/cux/om/lines/all/remove")
    @ResponseBody
    public ResponseData deleteLine(HttpServletRequest request, @RequestBody List<OmLinesAll> dto) {
        serviceLine.batchDelete(dto);
        return new ResponseData();
    }
}