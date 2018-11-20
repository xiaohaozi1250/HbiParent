package com.hand.hap.relink.service.impl;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.relink.dto.Link;
import com.hand.hap.relink.mapper.LinkMapper;
import com.hand.hap.relink.service.ILinkService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/5/13.
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class LinkService  extends BaseServiceImpl<Link> implements ILinkService,ProxySelf<Link> {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Link> queryList(Link link, int page, int pagesize){
        //PageHelper.startPage(page, pagesize);
        return linkMapper.myQuery(link);
    }
}
