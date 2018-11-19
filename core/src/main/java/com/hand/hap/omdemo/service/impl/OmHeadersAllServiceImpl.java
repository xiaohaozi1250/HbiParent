package com.hand.hap.omdemo.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hand.hap.omdemo.dto.OmHeadersAll;
import com.hand.hap.omdemo.service.IOmHeadersAllService;
import com.hand.hap.omdemo.mapper.OmHeadersAllMapper;
import com.hand.hap.omdemo.dto.OmLinesAll;
import com.hand.hap.omdemo.mapper.OmLinesAllMapper;
import com.hand.hap.core.IRequest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OmHeadersAllServiceImpl extends BaseServiceImpl<OmHeadersAll> implements IOmHeadersAllService {

    @Autowired
    private OmHeadersAllMapper headerMapper;
    @Autowired
    private OmLinesAllMapper lineMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteHeaders(List<OmHeadersAll> headers) {
        Iterator var2 = headers.iterator();

        while (var2.hasNext()) {
            OmHeadersAll header = (OmHeadersAll) var2.next();
            headerMapper.deleteByPrimaryKey(header);
            this.lineMapper.deleteByOmHeaderId(header.getOmHeaderId());
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<OmHeadersAll> batchUpdate(IRequest request, List<OmHeadersAll> headers) {
        for (OmHeadersAll header : headers) {
            if (header.getOmHeaderId() == null) {
                self().createOmHeadersAll(header);
            } else if (header.getOmHeaderId() != null) {
                self().updateOmHeadersAll(header);
            }
        }
        return headers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OmHeadersAll createOmHeadersAll(OmHeadersAll header) {
        headerMapper.insertSelective(header);
        if (header.getLines() != null) {
            for (OmLinesAll line : header.getLines()) {
                line.setOmHeaderId(header.getOmHeaderId());
                lineMapper.insertSelective(line);
            }
        }
        return header;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OmHeadersAll updateOmHeadersAll(OmHeadersAll header) {
        int updateCount = headerMapper.updateByPrimaryKeySelective(header);
        checkOvn(updateCount, header);
        if (header.getLines() != null) {
            for (OmLinesAll line : header.getLines()) {
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
