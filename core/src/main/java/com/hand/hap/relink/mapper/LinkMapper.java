package com.hand.hap.relink.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.relink.dto.Link;

import java.util.List;

/**
 * Created by Administrator on 2017/5/13.
 */
public interface LinkMapper extends Mapper<Link> {
     //查询
     List myQuery(Link link);

}
