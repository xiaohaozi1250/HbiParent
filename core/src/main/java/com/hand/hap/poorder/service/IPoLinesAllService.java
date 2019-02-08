package com.hand.hap.poorder.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.poorder.dto.PoLinesAll;
import com.hand.hap.system.service.IBaseService;

import java.util.List;

public interface IPoLinesAllService extends IBaseService<PoLinesAll>, ProxySelf<IPoLinesAllService> {

    void updateUnitPrice(IRequest iRequest, PoLinesAll poLinesAll) throws Exception;

    void updateQuantity(IRequest iRequest, PoLinesAll poLinesAll) throws Exception;

    List<PoLinesAll> submitSupport(IRequest iRequest, List<PoLinesAll> poLinesAllList) throws Exception;

}
