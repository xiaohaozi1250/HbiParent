package com.hand.hap.master_distributed_execution.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hap.master_distributed_execution.dto.MdType;
import com.hand.hap.master_distributed_execution.service.IMdTypeService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class MdTypeServiceImpl extends BaseServiceImpl<MdType> implements IMdTypeService{

}