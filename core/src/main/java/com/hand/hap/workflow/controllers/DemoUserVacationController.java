package com.hand.hap.workflow.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.workflow.dto.UserDemoVacation;
import com.hand.hap.workflow.service.IDemoUserVacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class DemoUserVacationController extends BaseController {

    @Autowired
    private IDemoUserVacationService service;


    /*
   * 提交请假流程demo
ß   * */
    @RequestMapping(value = "/dzh/demo/vacation/request", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseData createVacationProcessInstance(@RequestBody UserDemoVacation userDemoVacation,
                                                      HttpServletRequest httpRequest, HttpServletResponse response) {
        IRequest iRequest = createRequestContext(httpRequest);
        service.createVacationInstance(iRequest, userDemoVacation);
        return new ResponseData();
    }

    /*
    * 获取当前用户历史请假记录
    * */
    @ResponseBody
    @RequestMapping("/dzh/demo/vacation/query")
    public ResponseData getVacationHistory(HttpServletRequest request, HttpServletResponse response, UserDemoVacation userDemoVacation, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                           @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        List<UserDemoVacation> lists = service.selectVacationHistory(iRequest);
        return new ResponseData(lists);
    }

    /*
    请假流程通过businessKey获取流程表单
    * */
    @RequestMapping("/dzh/demo/vacation/{businessKey}")
    @ResponseBody
    public UserDemoVacation getProcessFrom(HttpServletRequest request, @PathVariable String businessKey) {
        UserDemoVacation userDemoVacation = new UserDemoVacation();
        userDemoVacation.setId(Long.parseLong(businessKey));
        UserDemoVacation demo = service.selectByPrimaryKey(createRequestContext(request), userDemoVacation);
        return demo;
    }
}