package com.hand.hap.demo.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hand.hap.demo.dto.OmHeaders;
import com.hand.hap.demo.service.IOmHeadersService;
import com.hand.hap.demo.mapper.OmHeadersMapper;
import com.hand.hap.demo.dto.OmLine;
import com.hand.hap.demo.mapper.OmLineMapper;
import com.hand.hap.core.IRequest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OmHeadersServiceImpl extends BaseServiceImpl<OmHeaders> implements IOmHeadersService {

    @Autowired
    private OmHeadersMapper headerMapper;
    @Autowired
    private OmLineMapper lineMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteHeaders(List<OmHeaders> headers) {
        Iterator var2 = headers.iterator();

        while (var2.hasNext()) {
            OmHeaders header = (OmHeaders) var2.next();
            headerMapper.deleteByPrimaryKey(header);
            this.lineMapper.deleteByOmHeaderId(header.getOmHeaderId());
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<OmHeaders> batchUpdate(IRequest request, List<OmHeaders> headers) {
        for (OmHeaders header : headers) {
            if (header.getOmHeaderId() == null) {
                self().createOmHeaders(header);
            } else if (header.getOmHeaderId() != null) {
                self().updateOmHeaders(header);
            }
        }
        return headers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OmHeaders createOmHeaders(OmHeaders header) {
        headerMapper.insertSelective(header);
        if (header.getLines() != null) {
            for (OmLine line : header.getLines()) {
                line.setOmHeaderId(header.getOmHeaderId());
                lineMapper.insertSelective(line);
            }
        }
        return header;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OmHeaders updateOmHeaders(OmHeaders header) {
        int updateCount = headerMapper.updateByPrimaryKeySelective(header);
        checkOvn(updateCount, header);
        if (header.getLines() != null) {
            for (OmLine line : header.getLines()) {
                if (line.getOmLineId() == null) {
                    line.setOmHeaderId(header.getOmHeaderId());
                    lineMapper.insertSelective(line);
                } else {
                    lineMapper.updateByPrimaryKeySelective(line);
                }
            }
        }
        return header;
    }
}
