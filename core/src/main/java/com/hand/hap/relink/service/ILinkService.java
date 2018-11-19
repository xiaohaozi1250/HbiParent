package com.hand.hap.relink.service;

import com.hand.hap.relink.dto.Link;
import com.hand.hap.system.service.IBaseService;

import java.util.List;

/**
 * Created by Administrator on 2017/5/13.
 */
public interface ILinkService extends IBaseService<Link> {

    //查询
    List<Link> queryList(Link link, int page, int pagesize);
}
