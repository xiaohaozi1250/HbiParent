package com.hand.hap.master_distributed_execution.controllers;

import com.hand.hap.code.rule.exception.CodeRuleException;
import com.hand.hap.intergration.annotation.HapOutbound;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.master_distributed_execution.dto.DistributeSituation;
import com.hand.hap.master_distributed_execution.service.IDistributeSituationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class DistributeSituationController extends BaseController {

    @Autowired
    private IDistributeSituationService service;
    @Autowired
    private RedisTemplate redisTemplate;

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

    @RequestMapping(value = "/hmdm/distribute/situation/invoke", method = {RequestMethod.POST})
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
    public ResponseData webSocketTest(@RequestBody List<DistributeSituation> dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession(true);
        //ResponseData responseData = new ResponseData();
        List<DistributeSituation> employees = new ArrayList<>();
        requestContext.setAttribute("SessionId", request.getRequestedSessionId());
        try {
            service.WebSocketDemo(requestContext, session);
        } catch (CodeRuleException e) {
            System.out.println("SessionId3：" + request.getRequestedSessionId());
        }
        return new ResponseData(employees);
    }

    @RequestMapping(value = "/hmdm/distribute/situation/redistest")
    @ResponseBody
    //计时器测试
    public ResponseData redisTest(HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        // 定义Key值
        String redisKey = "LIMIT_COUNT";
        //以增量的方式将long值存储在变量中
        Long count = redisTemplate.opsForValue().increment(redisKey, 1);
        if (count == 1) {
            //设置有效期为一分钟，1分钟后删除该key值
            redisTemplate.expire(redisKey, 60, TimeUnit.SECONDS);
        }
        if (count > 1) {
            responseData.setSuccess(false);
            responseData.setMessage("每分钟发送一次!");
        }
        return responseData;
    }

    @RequestMapping(value = "/hmdm/distribute/situation/poitest")
    @ResponseBody
    //poi报表生成
    public ResponseData poi(HttpServletRequest request, @RequestBody List<DistributeSituation> dtoList) {
        service.poiExport(request, dtoList);
        return new ResponseData();
    }

    /**
     * excel导入属性
     *
     * @param request
     * @param file
     * @return
     * @Date 2016/10/13
     */
    @RequestMapping(value = "/hmdm/distribute/situation/uploadExcel", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData uploadExcel(HttpServletRequest request, MultipartFile file) {
        IRequest iRequest = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        try {
            service.importExcel(iRequest, file);
        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setMessage(e.getLocalizedMessage());
        }
        return responseData;
    }
}