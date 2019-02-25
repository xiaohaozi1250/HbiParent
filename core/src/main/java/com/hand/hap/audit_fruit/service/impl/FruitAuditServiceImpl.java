package com.hand.hap.audit_fruit.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.audit.util.AuditRecordUtils;
import com.hand.hap.audit_fruit.dto.FruitAudit;
import com.hand.hap.audit_fruit.mapper.FruitAuditMapper;
import com.hand.hap.audit_fruit.service.IFruitAuditService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class FruitAuditServiceImpl extends BaseServiceImpl<FruitAudit> implements IFruitAuditService{

    @Autowired
    FruitAuditMapper fruitAuditMapper;

    @Override
    public List<Map<String, Object>> selectAuditRecord(IRequest iRequest, BaseDTO dto, int page, int pageSize) {
        // 分页
        PageHelper.startPage(page, pageSize);
        return AuditRecordUtils.operateAuditRecord(fruitAuditMapper.selectAuditRecord((FruitAudit) dto));
    }
    @Override
    public List selectAuditRecordDetail(IRequest iRequest, BaseDTO dto, int page, int pageSize) {
        // 分页
        PageHelper.startPage(page,pageSize);
        return AuditRecordUtils.operateAuditRecordSingleDetail(fruitAuditMapper.selectAuditRecordDetail((FruitAudit) dto));
    }




}