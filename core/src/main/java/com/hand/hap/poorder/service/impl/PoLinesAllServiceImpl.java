package com.hand.hap.poorder.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.poorder.mapper.PoLinesAllMapper;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.hand.hap.poorder.dto.PoLinesAll;
import com.hand.hap.poorder.service.IPoLinesAllService;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PoLinesAllServiceImpl extends BaseServiceImpl<PoLinesAll> implements IPoLinesAllService {
    @Autowired
    private PoLinesAllMapper lineMapper;

    //使用REQUIRED来统一事务处理
    //@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    //使用SUPPORTS来独立事务处理
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public List<PoLinesAll> submitSupport(IRequest iRequest, List<PoLinesAll> poLinesAllList) throws Exception {
        for (PoLinesAll poLinesAll : poLinesAllList) {
            //使用代理方法，需要去接口类定义该方法，并且需要使用self()调用
            self().updateUnitPrice(iRequest, poLinesAll);
            self().updateQuantity(iRequest, poLinesAll);
            //不用代理服务，没有使用到拦截器，直接使用自方法，哪怕声明了requried，拦截器也是不会拦截到事务
/*          updateUnitPrice(iRequest, poLinesAll);
            updateQuantity(iRequest, poLinesAll);*/
        }
        return poLinesAllList;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void updateQuantity(IRequest iRequest, PoLinesAll poLinesAll) throws Exception {
        lineMapper.updateQuantity(poLinesAll);
        if (poLinesAll.getPoHeaderId() == 10049) {
            throw new NullPointerException("Test Support Transaction");
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void updateUnitPrice(IRequest iRequest, PoLinesAll poLinesAll) throws Exception {
        lineMapper.updateUnitPrice(poLinesAll);
    }
}
