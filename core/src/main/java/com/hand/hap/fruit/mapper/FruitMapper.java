package com.hand.hap.fruit.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.fruit.dto.Fruit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhihao.dai on 2017/4/21.
 */
public interface FruitMapper extends Mapper<Fruit> {

    //更新
    int myUpdate(Fruit fruit);

    //删除
    int myDelete(Fruit fruit);

    //查询
    List myQuery(Fruit fruit);

    //LOV查询
    List lovQuery(Fruit fruit);


}
