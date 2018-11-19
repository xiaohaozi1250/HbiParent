package com.hand.hap.workflow.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.workflow.dto.UserDemoVacation;

import java.util.List;

public interface IDemoUserVacationService extends IBaseService<UserDemoVacation>, ProxySelf<IDemoUserVacationService>{

    //创建请假流程
    void createVacationInstance(IRequest iRequest, UserDemoVacation userDemoVacation);
    //查看历史请假记录
    List<UserDemoVacation> selectVacationHistory(IRequest iRequest);



}