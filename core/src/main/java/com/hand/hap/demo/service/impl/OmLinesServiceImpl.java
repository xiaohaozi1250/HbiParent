package com.hand.hap.demo.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hand.hap.demo.dto.OmLines;
import com.hand.hap.demo.service.IOmLinesService;

@Service
@Transactional(rollbackFor = Exception.class)
public class OmLinesServiceImpl extends BaseServiceImpl<OmLines> implements IOmLinesService {

}
