package com.hand.hap.audit_fruit.controllers;

import com.hand.hap.audit_fruit.dto.FruitAudit;
import com.hand.hap.audit_fruit.service.IFruitAuditService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FruitAuditController extends BaseController {

    @Autowired
    private IFruitAuditService service;


    @RequestMapping(value = "/fruit/a/query")
    @ResponseBody
    public ResponseData queryAuditRecord(HttpServletRequest request,
                                         @ModelAttribute FruitAudit dto,
                                         @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                         @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectAuditRecord(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/fruit/a/detail/{employeeId}")
    @ResponseBody
    public ResponseData queryAuditRecordDetail(HttpServletRequest request,
                                               @ModelAttribute FruitAudit dto,
                                               @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                               @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                               @PathVariable int id) {
        IRequest requestContext = createRequestContext(request);
        dto.setId((long) id);
        return new ResponseData(service.selectAuditRecordDetail(requestContext, dto, page, pageSize));
    }



}