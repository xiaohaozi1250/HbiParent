package com.hand.hap.master_distributed_execution.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.master_distributed_execution.mapper.DistributeSituationMapper;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hap.master_distributed_execution.dto.DistributeSituation;
import com.hand.hap.master_distributed_execution.service.IDistributeSituationService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class DistributeSituationServiceImpl extends BaseServiceImpl<DistributeSituation> implements IDistributeSituationService {
    @Autowired
    private DistributeSituationMapper distributeSituationMapper;

    //获取查询数据
    public List<DistributeSituation> selectDistributeSituation(DistributeSituation dto, IRequest request, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return distributeSituationMapper.selectDistributionData(dto);
    }
}