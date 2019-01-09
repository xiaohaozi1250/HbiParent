package com.hand.hap.ws.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.master_distributed_execution.dto.DistributeSituation;
import com.hand.hap.master_distributed_execution.dto.XmlDistributeSituation;
import com.hand.hap.master_distributed_execution.dto.XmlDistributeSituationList;
import com.hand.hap.master_distributed_execution.mapper.DistributeSituationMapper;
import com.hand.hap.master_distributed_execution.service.impl.DistributeSituationServiceImpl;
import com.hand.hap.ws.WsDistribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor by Val.Zhang
 * @mail wei.zhang12@hand-china.com
 * @date 2019/1/7
 */
@WebService(
        endpointInterface = "com.hand.hap.ws.WsDistribute",
        serviceName = "WsDistribute"
)
public class WsDistributeImpl implements WsDistribute {

    @Autowired
    private DistributeSituationMapper distributeSituationMapper;
    //在日志输出时可打印出日志信息所在类
    private final Logger logger = LoggerFactory.getLogger(DistributeSituationServiceImpl.class);

    //获取查询数据
    @Override
    public List<DistributeSituation> selectDistribute(XmlDistributeSituationList dtoList) {
        List<XmlDistributeSituation> xmlDistributeSituationList = dtoList.getDistributeSituationList();

        List<DistributeSituation> distributeSituations = new ArrayList<>();

        for (XmlDistributeSituation xmlDistributeSituation : xmlDistributeSituationList) {
            DistributeSituation dto = new DistributeSituation();
            dto.setItemCode(xmlDistributeSituation.getItemCode());
            distributeSituations.add(distributeSituationMapper.selectOne(dto));
        }
        return distributeSituations;
    }
}
