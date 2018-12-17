package com.hand.hap.podemo.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.hand.hap.podemo.dto.PoHeadersAll;
import com.hand.hap.podemo.service.IPoHeadersAllService;
import com.hand.hap.podemo.mapper.PoHeadersAllMapper;
import com.hand.hap.podemo.dto.PoLinesAll;
import com.hand.hap.podemo.mapper.PoLinesAllMapper;
import com.hand.hap.core.IRequest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PoHeadersAllServiceImpl extends BaseServiceImpl<PoHeadersAll> implements IPoHeadersAllService {

    @Autowired
    private PoHeadersAllMapper headerMapper;
    @Autowired
    private PoLinesAllMapper lineMapper;

    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<PoHeadersAll> selectDataWs(IRequest request, PoHeadersAll header, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        PoHeadersAll result = new PoHeadersAll();
        //List<PoHeadersAll> headers = headerMapper.selectData(header);
        List<PoHeadersAll> headers = headerMapper.select(header);
        for(int i = 0 ; i < headers.size() ; i++) {
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
