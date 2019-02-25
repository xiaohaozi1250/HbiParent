package com.hand.hap.audit_fruit.mapper;

import com.hand.hap.audit_fruit.dto.FruitAudit;
import com.hand.hap.mybatis.common.Mapper;

import java.util.List;
import java.util.Map;

public interface FruitAuditMapper extends Mapper<FruitAudit>{

    List<Map<String, Object>> selectAuditRecord(FruitAudit fruitAudit);

    List<Map<String, Object>> selectAuditRecordDetail(FruitAudit fruitAudit);


}