package com.hand.hap.poorder.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.poorder.dto.PoHeadersAll;
import com.hand.hap.poorder.dto.PoLinesAll;
import com.hand.hap.poorder.mapper.PoHeadersAllMapper;
import com.hand.hap.poorder.mapper.PoLinesAllMapper;
import com.hand.hap.poorder.service.IPoHeadersAllService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.hand.hap.core.IRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

@Service
public class PoHeadersAllServiceImpl extends BaseServiceImpl<PoHeadersAll> implements IPoHeadersAllService {

    @Autowired
    private PoHeadersAllMapper headerMapper;
    @Autowired
    private PoLinesAllMapper lineMapper;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean batchDeleteHeaders(List<PoHeadersAll> headers) {
        Iterator var2 = headers.iterator();

        while (var2.hasNext()) {
            PoHeadersAll header = (PoHeadersAll) var2.next();
            headerMapper.deleteByPrimaryKey(header);
            this.lineMapper.deleteByPoHeaderId(header.getPoHeaderId());
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<PoHeadersAll> batchUpdate(IRequest request, List<PoHeadersAll> headers) {
        for (PoHeadersAll header : headers) {
            if (header.getPoHeaderId() == null) {
                self().createPoHeadersAll(header);
            } else if (header.getPoHeaderId() != null) {
                self().updatePoHeadersAll(header);
            }
        }
        return headers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PoHeadersAll createPoHeadersAll(PoHeadersAll header) {
        headerMapper.insertSelective(header);
        if (header.getLines() != null) {
            for (PoLinesAll line : header.getLines()) {
                line.setPoHeaderId(header.getPoHeaderId());
                lineMapper.insertSelective(line);
            }
        }
        return header;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PoHeadersAll updatePoHeadersAll(PoHeadersAll header) {
        int updateCount = headerMapper.updateByPrimaryKeySelective(header);
        checkOvn(updateCount, header);
        if (header.getLines() != null) {
            for (PoLinesAll line : header.getLines()) {
                if (line.getPoLineId() == null) {
                    line.setPoHeaderId(header.getPoHeaderId());
                    lineMapper.insertSelective(line);
                } else {
                    lineMapper.updateByPrimaryKeySelective(line);
                }
            }
        }
        return header;
    }

    public List<PoHeadersAll> selectDataWs(IRequest request, PoHeadersAll header, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        PoHeadersAll result = new PoHeadersAll();
        //List<PoHeadersAll> headers = headerMapper.selectData(header);
        List<PoHeadersAll> headers = headerMapper.select(header);
        for (int i = 0; i < headers.size(); i++) {
            PoHeadersAll poHeaders = headers.get(i);
            result = poHeaders;
            PoLinesAll line = new PoLinesAll();
            line.setPoHeaderId(poHeaders.getPoHeaderId());
            List<PoLinesAll> poLinesAll = lineMapper.select(line);
            if (!poLinesAll.isEmpty()) {
                result.setLines(poLinesAll);
            }
        }
        return headers;
    }
}
