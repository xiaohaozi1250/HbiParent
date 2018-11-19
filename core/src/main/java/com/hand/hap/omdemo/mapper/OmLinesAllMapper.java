package com.hand.hap.omdemo.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.omdemo.dto.OmLinesAll;
public interface OmLinesAllMapper extends Mapper<OmLinesAll>{
    int deleteByOmHeaderId(Long var1);
}
