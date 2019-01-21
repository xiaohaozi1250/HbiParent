package com.hand.hap.master_distributed_execution.service;

import com.hand.hap.code.rule.exception.CodeRuleException;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.master_distributed_execution.dto.DistributeSituation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface IDistributeSituationService extends IBaseService<DistributeSituation>, ProxySelf<IDistributeSituationService> {

    //获取查询数据
    List<DistributeSituation> selectDistributeSituation(DistributeSituation dto, IRequest request, int page, int pagesize);

    String inAction() throws Exception;

    void invoke(List<DistributeSituation> dtoList) throws Exception;

    void WedSocketDemo(IRequest requestCtx, HttpSession session) throws CodeRuleException;
}