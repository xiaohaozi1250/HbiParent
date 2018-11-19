package com.hand.hap.workflow.service.impl;

import com.hand.hap.activiti.service.IActivitiService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hap.workflow.mapper.DemoUserVacationMapper;
import org.activiti.rest.service.api.engine.variable.RestVariable;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hap.workflow.dto.UserDemoVacation;
import com.hand.hap.workflow.service.IDemoUserVacationService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class DemoUserVacationServiceImpl extends BaseServiceImpl<UserDemoVacation> implements IDemoUserVacationService {

    @Autowired
    private IActivitiService activitiService;

    @Autowired
    private DemoUserVacationMapper demoVacationMapper;

    private final String processDefinitionKey = "VACATION_REQUEST_DEMO";

    /*
* 创建请假流程，将表单存放在dzh_demo_vacation 表
* */
    @Override
    public void createVacationInstance(IRequest iRequest, UserDemoVacation userDemoVacation) {
        userDemoVacation.setUserCode(iRequest.getEmployeeCode());
        UserDemoVacation demo = this.insertSelective(iRequest, userDemoVacation);
        //创建工作流实例请求对象
        ProcessInstanceCreateRequest instanceCreateRequest = new ProcessInstanceCreateRequest();
        instanceCreateRequest.setBusinessKey(String.valueOf(demo.getId()));
        instanceCreateRequest.setProcessDefinitionKey(processDefinitionKey);
        //添加流程变量
        List<RestVariable> variables = new ArrayList<>();
        RestVariable variable = new RestVariable();
        variable.setName("needDays");
        variable.setType("long");
        variable.setValue(demo.getNeedDays());

        variables.add(variable);
        instanceCreateRequest.setVariables(variables);

        activitiService.startProcess(iRequest, instanceCreateRequest);
    }


    /*
    * 查看历史请假记录
    * */
    @Override
    public List<UserDemoVacation> selectVacationHistory(IRequest iRequest) {

        UserDemoVacation userDemoVacation = new UserDemoVacation();
        userDemoVacation.setUserCode(iRequest.getEmployeeCode());
        List<UserDemoVacation> demoUserVacations = demoVacationMapper.select(userDemoVacation);
        return demoUserVacations;
    }



}