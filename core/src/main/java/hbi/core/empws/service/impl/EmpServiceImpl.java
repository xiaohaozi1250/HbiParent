package hbi.core.empws.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.intergration.dto.HapInterfaceHeader;
import com.hand.hap.intergration.service.IHapApiService;
import com.hand.hap.intergration.service.IHapInterfaceHeaderService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hbi.core.empws.dto.Emp;
import hbi.core.empws.dto.EmpBaseResponse;

import hbi.core.empws.dto.JsonDateValueProcessor;
import hbi.core.empws.exception.EmpBaseException;
import hbi.core.empws.mapper.EmpMapper;
import hbi.core.empws.service.IEmpService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional(rollbackFor = Exception.class)
public class EmpServiceImpl extends BaseServiceImpl<Emp> implements IEmpService {
    //不做介绍
    @Autowired
    private EmpMapper empMapper;
    //在日志输出时可打印出日志信息所在类
    private final Logger logger = LoggerFactory.getLogger(EmpServiceImpl.class);
    @Autowired
    private IHapInterfaceHeaderService headerService;
    @Resource(name = "restBean")
    private IHapApiService restService;

    //事务回滚
    //针对单条数据处理
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public EmpBaseResponse handleEmp(IRequest request, Emp emp) throws EmpBaseException {
        EmpBaseResponse empBaseResponse = new EmpBaseResponse();
        if (logger.isDebugEnabled()) {
            //Debug logger 在控制台输出以便调试
            logger.debug("员工ID : {}", emp.getEmployeeId());
        }
        if (emp.getEmployeeId() == null) {
            //自定义异常抛出:
            throw new EmpBaseException("com.hand.hap.error.IdIsNull", 10001, "ID is null");
        } else {
            //逻辑实现代码
            Emp queryDto = new Emp();
            queryDto.setEmployeeId(emp.getEmployeeId());
            List<Emp> results = empMapper.select(queryDto);
            empBaseResponse.setData(results);
        }

        return empBaseResponse;
    }

    //事务回滚
    //针对全部数据处理
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<EmpBaseResponse> handleEmp(IRequest request, List<Emp> emp) throws EmpBaseException {
        List<EmpBaseResponse> empBaseResponses = new ArrayList<>();
        for (int i = 0; i < emp.size(); i++) {
            Emp employee = emp.get(i);
            EmpBaseResponse empBaseResponse = new EmpBaseResponse();
            if (logger.isDebugEnabled()) {
                //Debug logger 在控制台输出以便调试
                logger.debug("员工ID : {}", employee.getEmployeeId());
            }
            if (employee.getEmployeeId() == null) {
                //自定义异常抛出:
                throw new EmpBaseException("com.hand.hap.error.IdIsNull", 10001, "ID is null");
            } else {
                //逻辑实现代码
                Emp queryDto = new Emp();
                queryDto.setEmployeeId(employee.getEmployeeId());
                List<Emp> results = empMapper.select(queryDto);

                empBaseResponse.setData(results);
                empBaseResponses.add(empBaseResponse);
            }
        }
        return empBaseResponses;
    }

    //事务回滚
    //针对全部数据处理
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public EmpBaseResponse handleEmp1(IRequest request, List<Emp> emp) throws EmpBaseException {
        EmpBaseResponse empBaseResponse = new EmpBaseResponse();
        List<Emp> results = new ArrayList<>();
        for (int i = 0; i < emp.size(); i++) {
            Emp employee = emp.get(i);
            if (logger.isDebugEnabled()) {
                //Debug logger 在控制台输出以便调试
                logger.debug("员工ID : {}", employee.getEmployeeId());
            }
            if (employee.getEmployeeId() == null) {
                //自定义异常抛出:
                throw new EmpBaseException("com.hand.hap.error.IdIsNull", 10001, "ID is null");
            } else {
                //逻辑实现代码
                Emp queryDto = new Emp();
                queryDto.setEmployeeId(employee.getEmployeeId());
                List<Emp> result_query = empMapper.select(queryDto);
                results.addAll(result_query);
            }
            empBaseResponse.setData(results);
        }
        return empBaseResponse;
    }


    //事务回滚
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Emp inAction(IRequest request, Emp emp) throws Exception {
        JSONObject Response_Json;
        JSONObject jsonObj;
        JSONArray jsonArray;
        Long employeeId = emp.getEmployeeId();
        String string = "{\"employeeId\":" + employeeId + "}";
        logger.debug("请求 : ", string);
        System.out.println("请求 : " + string);
        try {
            JsonConfig jsonConfig = new JsonConfig();
            //注册时间字段处理器
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            //将数据转换成json格式，并讲json日期格式利用时间字段处理器转换成指定格式
            jsonObj = JSONObject.fromObject(string, jsonConfig);
            HapInterfaceHeader hapInterfaceHeader = this.headerService.getHeaderAndLine("RestApiEmpDemo", "REST1");
            Response_Json = this.restService.invoke(hapInterfaceHeader, jsonObj);
            System.out.println("请求报文：" + jsonObj);
            System.out.println("返回报文：" + Response_Json);
            if (Response_Json != null) {
                logger.debug("返回报文 : ", Response_Json.toString());
            }

            jsonArray = Response_Json.getJSONArray("rows");
            for (int i = 0; i < jsonArray.size(); i++) {
                emp.setEmployeeId(jsonArray.getJSONObject(i).getLong("employeeId"));
                emp.setEmployeeCode(jsonArray.getJSONObject(i).getString("employeeCode"));
                //日期格式先转换成String，然后再存入
                String date = jsonArray.getJSONObject(i).get("bornDate").toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                emp.setBornDate(sdf.parse(date));
            }

        } catch (Exception e) {
            logger.error("SynRemotesInsertMainError : ", e);
            throw e;
        }
        return emp;
    }

    //事务回滚
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void inAction2(IRequest request) throws Exception {
        JSONObject Response_Json;
        JSONObject jsonObj;
        JSONArray jsonArray = new JSONArray();

        //String string = "{\"emp\":[{\"employeeId\":" + 10003 + "},{\"employeeId\":" + 10005 + "}]}";
        String string = "{\"emp\":[{\"employeeId\":10003,\"name\":\"测试员101\"}]}";
        System.out.println("请求 : " + string);
        try {
            JsonConfig jsonConfig = new JsonConfig();
            //注册时间字段处理器
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            //将数据转换成json格式，并讲json日期格式利用时间字段处理器转换成指定格式
            jsonObj = JSONObject.fromObject(string, jsonConfig);
            HapInterfaceHeader hapInterfaceHeader = this.headerService.getHeaderAndLine("RestApiEmpDemo", "REST3");
            Response_Json = this.restService.invoke(hapInterfaceHeader, jsonObj);
            System.out.println("请求报文：" + jsonObj);
            System.out.println("返回报文：" + Response_Json);

        } catch (Exception e) {
            logger.error("SynRemotesInsertMainError : ", e);
            throw e;
        }

    }
}