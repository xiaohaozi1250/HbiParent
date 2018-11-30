package com.hand.hap.demo.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hand.hap.demo.dto.OmLine;
import com.hand.hap.demo.service.IOmLineService;

@Service
@Transactional(rollbackFor = Exception.class)
public class OmLineServiceImpl extends BaseServiceImpl<OmLine> implements IOmLineService {

}
