package com.hand.hap.podemo.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hand.hap.podemo.dto.PoLinesAll;
import com.hand.hap.podemo.service.IPoLinesAllService;

@Service
@Transactional(rollbackFor = Exception.class)
public class PoLinesAllServiceImpl extends BaseServiceImpl<PoLinesAll> implements IPoLinesAllService {

}
