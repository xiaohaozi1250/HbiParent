package com.hand.hap.poorder.controllers;

import com.hand.hap.intergration.annotation.HapInbound;
import com.hand.hap.poorder.dto.PoHeadersAll;
import com.hand.hap.poorder.dto.PoLinesAll;
import com.hand.hap.poorder.service.IPoHeadersAllService;
import com.hand.hap.poorder.service.IPoLinesAllService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PoHeadersAllController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(PoHeadersAllController.class);
    //test
    @Autowired
    private IPoHeadersAllService service;

    @Autowired
    private IPoLinesAllService serviceLine;

    @RequestMapping(value = "/cux/po/headers/all/query")
    @ResponseBody
    public ResponseData query(PoHeadersAll dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/cux/po/headers/all/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<PoHeadersAll> dto, BindingResult result, HttpServletRequest request) {
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

    @RequestMapping(value = "/cux/po/headers/all/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<PoHeadersAll> dto) {
        this.service.batchDeleteHeaders(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/cux/po/lines/all/query")
    @ResponseBody
    public ResponseData queryLine(PoLinesAll dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(serviceLine.select(requestContext, dto, page, pageSize));
    }

/*    @RequestMapping(value = "/cux/po/lines/all/submit")
    @ResponseBody
    public ResponseData updateLine(@RequestBody List<PoLinesAll> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(serviceLine.batchUpdate(requestCtx, dto));
    }*/

    @RequestMapping(value = "/cux/po/lines/all/remove")
    @ResponseBody
    public ResponseData deleteLine(HttpServletRequest request, @RequestBody List<PoLinesAll> dto) {
        serviceLine.batchDelete(dto);
        return new ResponseData();
    }

    //ws地址,主要是/api/public/com/hand/hap/interface
    //如果需要加密，则把地址的public去掉
    //则需要进行OAuth2校验即可直接使用接口
    @RequestMapping(value = "/api/com/hand/poHeaders", method = RequestMethod.POST)
    @ResponseBody
//ws名称 无特别含义
    @HapInbound(apiName = "com.hand.hap.test.integration.poheaders")
    public ResponseData queryWS(@RequestBody PoHeadersAll dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        //Criteria criteria = new Criteria();
        //return new ResponseData(service.selectOptions(requestContext, dto, criteria));
        //return new ResponseData(service.select(requestContext, dto, page, pageSize));
        return new ResponseData(service.selectDataWs(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/cux/po/lines/all/submit")
    @ResponseBody
    public ResponseData supportSumit1(@RequestBody List<PoLinesAll> dto, BindingResult result, HttpServletRequest request) {
        ResponseData responseData = new ResponseData(false);
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        try {
            return new ResponseData(serviceLine.submitSupport(requestCtx, dto));
        } catch (Exception e) {
            logger.error("SynRemotesInsertMainError : ", e);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }
}