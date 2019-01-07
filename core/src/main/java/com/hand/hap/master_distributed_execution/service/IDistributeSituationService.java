package com.hand.hap.master_distributed_execution.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.master_distributed_execution.dto.DistributeSituation;

import java.util.List;

public interface IDistributeSituationService extends IBaseService<DistributeSituation>, ProxySelf<IDistributeSituationService> {

    //获取查询数据
    List<DistributeSituation> selectDistributeSituation(DistributeSituation dto, IRequest request, int page, int pagesize);

    String inAction() throws Exception;

    void invoke(DistributeSituation dto) throws Exception;
}