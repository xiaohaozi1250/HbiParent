package hbi.core.mydemo.controllers;

import com.hand.hap.intergration.annotation.HapInbound;
import hbi.core.mydemo.dto.*;
import hbi.core.mydemo.exception.EmpBaseException;
import hbi.core.mydemo.service.IRestApiService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import hbi.core.mydemo.service.IEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import hbi.core.mydemo.dto.EmpBaseResponse.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class EmpController extends BaseController {

    @Autowired
    private IEmpService service;

    @Autowired
    private IRestApiService iRestApiService;

    private final Logger logger = LoggerFactory.getLogger(EmpController.class);

    @RequestMapping(value = "/demo/emp/query")
    @ResponseBody
    public ResponseData query(Emp dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<Emp> emps = service.select(requestContext, dto, page, pageSize);
        return new ResponseData(emps);
    }

    @RequestMapping(value = "/demo/emp/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Emp> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        IRequest requestContext = createRequestContext(request);
        System.out.println("SessionId：" + request.getRequestedSessionId());
        requestContext.setAttribute("SessionId", request.getRequestedSessionId());
        service.WebSocketDemo(requestContext);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/demo/emp/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<Emp> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/api/public/com/hand/hap/interface/emp3", method = RequestMethod.POST)
    @ResponseBody
    //ws名称 无特别含义
    @HapInbound(apiName = "com.hand.hap.test.integration.emp")
    public ResponseData queryWs(@RequestBody Emp dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        //System.out.println("haha--"+dto.getEmployeeId());
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    //ws地址,主要是/api
    //HAP中内置了对/api开头的资源访问监控
    @RequestMapping(value = "/api/public/com/hand/hap/interface/emp", method = RequestMethod.POST)
    @ResponseBody
    //ws名称 无特别含义
    @HapInbound(apiName = "com.hand.hap.test.integration.emp")
    public List<EmpBaseResponse> queryWs(@RequestBody List<Emp> dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                         @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<EmpBaseResponse> responses = new ArrayList<>();

        for (Emp emp : dto) {
            EmpBaseResponse response = new EmpBaseResponse();
            try {
                response = service.handleEmp(requestContext, emp);
            } catch (Exception e) {
                //获取handleEmp抛出的错误信息
                Status status = new Status();
                //if判断是否自定义异常
                if (e instanceof EmpBaseException) {
                    //获取自定义异常Code,在service中定义
                    status.setCode(((EmpBaseException) e).getCode());
                } else {
                    //设置不可预知的异常Code(具体参数看项目是否有做要求)
                    status.setCode(10000);
                }
                //获取抛出的异常信息
                status.setMessage(e.getMessage());
                response.setSuccess(false);
                response.setStatus(status);
            }
            responses.add(response);
        }
        return responses;
    }

    @RequestMapping(value = "/api/public/com/hand/hap/interface/emp1", method = RequestMethod.POST)
    @ResponseBody
    //ws名称 无特别含义
    @HapInbound(apiName = "com.hand.hap.test.integration.emp1")
    public ResponseData queryWs1(@RequestBody List<Emp> dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        EmpBaseResponse response = new EmpBaseResponse();
        try {
            //调用service.handleEmp1
            response = service.handleEmp1(requestContext, dto);
        } catch (Exception e) {
            //获取handleEmp抛出的错误信息
            Status status = new Status();
            //if判断是否自定义异常
            if (e instanceof EmpBaseException) {
                //获取自定义异常Code,在service中定义
                status.setCode(((EmpBaseException) e).getCode());
            } else {
                //设置不可预知的异常Code(具体参数看项目是否有做要求)
                status.setCode(10000);
            }
            //获取抛出的异常信息
            status.setMessage(e.getMessage());
            response.setSuccess(false);
            response.setStatus(status);
        }
        return response;
    }

    @RequestMapping(value = "/api/public/com/hand/hap/interface/emp4", method = RequestMethod.POST)
    @ResponseBody
    //ws名称 无特别含义
    @HapInbound(apiName = "com.hand.hap.test.integration.emp4")
    public EmpList queryWs(@RequestBody EmpList dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                           @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        EmpList empBaseResponseList = new EmpList();
        List<EmpBaseResponse> responses = new ArrayList<>();
        List<Emp> empList = dto.getEmp();
        for (Emp emp : empList) {
            EmpBaseResponse response = new EmpBaseResponse();
            try {
                response = service.handleEmp(requestContext, emp);
            } catch (Exception e) {
                //获取handleEmp抛出的错误信息
                Status status = new Status();
                //if判断是否自定义异常
                if (e instanceof EmpBaseException) {
                    //获取自定义异常Code,在service中定义
                    status.setCode(((EmpBaseException) e).getCode());
                } else {
                    //设置不可预知的异常Code(具体参数看项目是否有做要求)
                    status.setCode(10000);
                }
                //获取抛出的异常信息
                status.setMessage(e.getMessage());
                response.setSuccess(false);
                response.setStatus(status);
            }
            responses.add(response);
        }
        empBaseResponseList.setEmpBaseResponse(responses);
        return empBaseResponseList;
    }

    @RequestMapping(value = "/demo/emp/invoke")
    @ResponseBody
    public ResponseData invoke(Emp dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                               @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        List<Emp> employees = new ArrayList<>();
        Emp employee = new Emp();
        //根据传入的ID，获取数据
        //这里是模拟，如果是实际
        try {
            //这里只是模拟解析，实际入参可根据情况改写
            //获取解析数据
            employee = service.inAction(requestContext, dto);
            System.out.println("Id：" + employee.getEmployeeId());
            System.out.println("编码：" + employee.getEmployeeCode());
            System.out.println("生日：" + employee.getBornDate());
        } catch (Exception e) {
            System.out.println("invoke Error");
        }
        employees.add(employee);
        //返回请求
        return new ResponseData(employees);
    }

    @RequestMapping(value = "/demo/emp/invoke1")
    @ResponseBody
    public ResponseData invoke1(@RequestBody List<Emp> dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        List<Emp> employees = new ArrayList<>();
        System.out.println("SessionId：" + request.getRequestedSessionId());
        requestContext.setAttribute("SessionId", request.getRequestedSessionId());
        service.WebSocketDemo(requestContext);
/*        try {
            service.inAction2(requestContext);
*//*            for (Emp employee : dto) {
                System.out.println("Id：" + employee.getEmployeeId());
                System.out.println("编码：" + employee.getEmployeeCode());
                System.out.println("生日：" + employee.getBornDate());
                Emp emp = service.inAction1(requestContext, employee);
                System.out.println("解析获取的 Id：" + emp.getEmployeeId());
                System.out.println("解析获取的 编码：" + emp.getEmployeeCode());
                System.out.println("解析获取的 生日：" + emp.getBornDate());
                employees.add(emp);
            }*//*
        } catch (Exception e) {
            System.out.println("invoke Error");
        }*/
        //返回请求
        return new ResponseData(employees);
    }
}