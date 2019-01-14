package com.hand.hap.master_distributed_execution.controllers;

import com.hand.hap.intergration.annotation.HapOutbound;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.master_distributed_execution.dto.DistributeSituation;
import com.hand.hap.master_distributed_execution.service.IDistributeSituationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class DistributeSituationController extends BaseController {

    @Autowired
    private IDistributeSituationService service;

    @RequestMapping(value = "/hmdm/distribute/situation/query")
    @ResponseBody
    public ResponseData query(@RequestBody DistributeSituation dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectDistributeSituation(dto, requestContext, page, pageSize));
        //return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/hmdm/distribute/situation/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<DistributeSituation> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hmdm/distribute/situation/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<DistributeSituation> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/hmdm/distribute/situation/invoke")
    @ResponseBody
    @HapOutbound(apiName = "DistributeWs")
    public void invoke(@RequestBody List<DistributeSituation> dtoList, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        try {
            //获取解析数据
            service.invoke(dtoList);
        } catch (Exception e) {
            System.out.println("invoke Error");
        }

    }

    @RequestMapping(value = "/hmdm/distribute/situation/webSocketTest")
    @ResponseBody
    public ResponseData invoke1(@RequestBody List<DistributeSituation> dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        //ResponseData responseData = new ResponseData();
        List<DistributeSituation> employees = new ArrayList<>();
        System.out.println("SessionId2：" + request.getRequestedSessionId());
        requestContext.setAttribute("SessionId", request.getRequestedSessionId());
        service.WebSocketTest(requestContext);
        return new ResponseData(employees);
    }

    @RequestMapping(value = "/hmdm/distribute/situation/redistest")
    @ResponseBody
    public ResponseData redisTest(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        RedisTemplate redisTemplate = new RedisTemplate();
        String redisKey = "LIMIT_COUNT";
        Long count = redisTemplate.opsForValue().increment(redisKey, 1);
        if (count == 1) {
            //设置有效期为一分钟
            redisTemplate.expire(redisKey, 60, TimeUnit.SECONDS);
        }
        if (count > 1) {
            responseData.setSuccess(false);
            responseData.setMessage("每分钟发送一次!");
        }
        return responseData;
    }
}