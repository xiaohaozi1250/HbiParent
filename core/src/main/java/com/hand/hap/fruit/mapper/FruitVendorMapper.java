package com.hand.hap.fruit.mapper;

import com.hand.hap.fruit.dto.FruitVendor;
import com.hand.hap.mybatis.common.Mapper;

import java.util.List;

/**
 * Created by zhihao.dai  on 2017/5/22.
 */
public interface FruitVendorMapper extends Mapper<FruitVendor> {

    //查询
    List myQuery(FruitVendor fruitVendor);

}
