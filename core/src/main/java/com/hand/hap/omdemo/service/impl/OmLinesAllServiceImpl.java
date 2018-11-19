package com.hand.hap.omdemo.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hand.hap.omdemo.dto.OmLinesAll;
import com.hand.hap.omdemo.service.IOmLinesAllService;

@Service
@Transactional(rollbackFor = Exception.class)
public class OmLinesAllServiceImpl extends BaseServiceImpl<OmLinesAll> implements IOmLinesAllService {

}
