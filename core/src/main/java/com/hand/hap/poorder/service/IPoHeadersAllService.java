package com.hand.hap.poorder.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.poorder.dto.PoHeadersAll;
import com.hand.hap.core.IRequest;
import java.util.List;

public interface IPoHeadersAllService extends IBaseService<PoHeadersAll>, ProxySelf<IPoHeadersAllService> {

    boolean batchDeleteHeaders(List<PoHeadersAll> headers);

    List<PoHeadersAll> batchUpdate(IRequest request, List<PoHeadersAll> headers);

    PoHeadersAll createPoHeadersAll(PoHeadersAll header);

    PoHeadersAll updatePoHeadersAll(PoHeadersAll header);

    List<PoHeadersAll> selectDataWs(IRequest request, PoHeadersAll header, int page, int pagesize);
}
