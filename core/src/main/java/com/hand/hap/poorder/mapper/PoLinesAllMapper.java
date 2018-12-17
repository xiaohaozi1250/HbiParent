package com.hand.hap.poorder.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.poorder.dto.PoLinesAll;

public interface PoLinesAllMapper extends Mapper<PoLinesAll>{
    int deleteByPoHeaderId(Long var1);
}
