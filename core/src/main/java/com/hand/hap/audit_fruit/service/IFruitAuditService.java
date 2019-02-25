package com.hand.hap.audit_fruit.service;

import com.hand.hap.audit_fruit.dto.FruitAudit;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hap.system.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface IFruitAuditService extends IBaseService<FruitAudit>, ProxySelf<IFruitAuditService>{

    public List<Map<String, Object>> selectAuditRecord(IRequest iRequest, BaseDTO dto, int page, int pageSize) ;

    public List selectAuditRecordDetail(IRequest iRequest, BaseDTO dto, int page, int pageSize) ;


}