package com.hand.hap.demo.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.demo.dto.OmHeaders;
import com.hand.hap.core.IRequest;
import java.util.List;

public interface IOmHeadersService extends IBaseService<OmHeaders>, ProxySelf<IOmHeadersService> {

    boolean batchDeleteHeaders(List<OmHeaders> headers);

    List<OmHeaders> batchUpdate(IRequest request, List<OmHeaders> headers);

    OmHeaders createOmHeaders(OmHeaders header);

    OmHeaders updateOmHeaders(OmHeaders header);
}
