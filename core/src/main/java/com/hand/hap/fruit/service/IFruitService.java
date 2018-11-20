package com.hand.hap.fruit.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.fruit.dto.FruitVendor;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.fruit.dto.Fruit;

import java.util.List;

/**
 * Created by zhihao.dai on 2017/4/21.
 */
public interface IFruitService extends IBaseService<Fruit> {
    //更新
    int updateList(List<Fruit> fruitList);

    //删除
    int deleteList(List<Fruit> fruitList);

    //查询
    List<Fruit> queryList(Fruit fruit,int page, int pagesize);

    //行查询
    List<FruitVendor> queryLineList(FruitVendor fruitVendor, int page, int pagesize);

    //提交
    List<Fruit>  batchLineUpdate(IRequest request,List<Fruit> fruitList);

}
