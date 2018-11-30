package com.hand.hap.demo.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.demo.dto.OmLine;
public interface OmLineMapper extends Mapper<OmLine>{
    int deleteByOmHeaderId(Long var1);
}
