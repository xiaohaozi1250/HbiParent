package com.hand.hap.master_distributed_execution.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.master_distributed_execution.dto.DistributeSituation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DistributeSituationMapper extends Mapper<DistributeSituation> {
    //获取主数据分布执行信息
    List<DistributeSituation> selectDistributionData(DistributeSituation distributeSituation);

    //获取数据编码
    List<DistributeSituation> selectItemCode(DistributeSituation dto);

    //获取分发状态
    List<DistributeSituation> selectStatus(DistributeSituation dto);

    //获取分发批次号
    List<DistributeSituation> selectBatchNum(DistributeSituation dto);

    //获取组织
    List<DistributeSituation> selectOrganizationCode(DistributeSituation dto);

    //获取分发申请人
    List<DistributeSituation> selectCreatedBy();

    Long getHeaderId(@Param("itemCode") String itemCode);
}
