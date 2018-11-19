package com.hand.hap.omdemo.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.omdemo.dto.OmHeadersAll;
import com.hand.hap.core.IRequest;
import java.util.List;

public interface IOmHeadersAllService extends IBaseService<OmHeadersAll>, ProxySelf<IOmHeadersAllService> {

    boolean batchDeleteHeaders(List<OmHeadersAll> headers);

    List<OmHeadersAll> batchUpdate(IRequest request, List<OmHeadersAll> headers);

    OmHeadersAll createOmHeadersAll(OmHeadersAll header);

    OmHeadersAll updateOmHeadersAll(OmHeadersAll header);
}
