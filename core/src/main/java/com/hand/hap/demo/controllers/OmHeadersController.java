package com.hand.hap.demo.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.demo.dto.OmLine;
import com.hand.hap.demo.service.IOmLineService;
import com.hand.hap.demo.dto.OmHeaders;
import com.hand.hap.demo.service.IOmHeadersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class OmHeadersController extends BaseController {
    //test
    @Autowired
    private IOmHeadersService service;

    @Autowired
    private IOmLineService serviceLine;

    @RequestMapping(value = "/cux/om/headers/query")
    @ResponseBody
    public ResponseData query(OmHeaders dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/cux/om/headers/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<OmHeaders> dto, BindingResult result, HttpServletRequest request) {
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

    @RequestMapping(value = "/cux/om/headers/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<OmHeaders> dto) {
        this.service.batchDeleteHeaders(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/cux/om/lines/query")
    @ResponseBody
    public ResponseData queryLine(OmLine dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(serviceLine.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/cux/om/lines/submit")
    @ResponseBody
    public ResponseData updateLine(@RequestBody List<OmLine> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(serviceLine.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/cux/om/lines/remove")
    @ResponseBody
    public ResponseData deleteLine(HttpServletRequest request, @RequestBody List<OmLine> dto) {
        serviceLine.batchDelete(dto);
        return new ResponseData();
    }
    //测试分支使用
}