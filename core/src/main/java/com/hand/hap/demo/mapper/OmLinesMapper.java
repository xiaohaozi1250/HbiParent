package com.hand.hap.demo.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.demo.dto.OmLines;
public interface OmLinesMapper extends Mapper<OmLines>{
    int deleteByOmHeaderId(Long var1);
}
