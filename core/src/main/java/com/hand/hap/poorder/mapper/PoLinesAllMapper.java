package com.hand.hap.podemo.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.podemo.dto.PoLinesAll;
public interface PoLinesAllMapper extends Mapper<PoLinesAll>{
    int deleteByPoHeaderId(Long var1);
}
