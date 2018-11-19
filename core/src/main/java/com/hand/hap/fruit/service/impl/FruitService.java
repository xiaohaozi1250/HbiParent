package com.hand.hap.fruit.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.fruit.dto.FruitVendor;
import com.hand.hap.fruit.mapper.FruitVendorMapper;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hap.fruit.dto.Fruit;
import com.hand.hap.fruit.mapper.FruitMapper;
import com.hand.hap.fruit.service.IFruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhihao.dai on 2017/4/21.
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class FruitService extends BaseServiceImpl<Fruit> implements IFruitService,ProxySelf<Fruit> {
    @Autowired
    private FruitMapper fruitMapper;
    @Autowired
    private FruitVendorMapper fruitVendorMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor =
            Exception.class)
    public int updateList(List<Fruit> fruitList) {
        //fruitList.forEach(fruit -> checkOvn(fruitMapper.myUpdate(fruit),fruit));
        for (Fruit fruit : fruitList) {
                //System.out.println("fruit"+fruit.getName());
                fruitMapper.myUpdate(fruit);
        }
            return fruitList.size();
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor =
            Exception.class)
    public int deleteList(List<Fruit> fruitList) {
        //fruitList.forEach(fruit -> checkOvn(fruitMapper.myUpdate(fruit),fruit));
        for (Fruit fruit : fruitList) {
            //System.out.println("fruit"+fruit.getName());
            fruitMapper.myDelete(fruit);
        }
        return fruitList.size();
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Fruit> queryList(Fruit fruit,int page, int pagesize){
        PageHelper.startPage(page, pagesize);
       return fruitMapper.myQuery(fruit);
    }



    //行查询
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<FruitVendor> queryLineList(FruitVendor fruitVendor, int page, int pagesize){
        PageHelper.startPage(page, pagesize);
        return fruitVendorMapper.myQuery(fruitVendor);
    }


    //提交
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor =
            Exception.class)
    public List<Fruit> batchLineUpdate(IRequest request, List<Fruit> fruitList) {
        for (Fruit fruit : fruitList) {
            if (fruit.getId() == null) {
                createFruit(request, fruit);
            } else if (fruit.getId()  != null) {
                updateFruit(request, fruit);
            }
        }
        return fruitList;
    }
    //保存头
    public Fruit createFruit(IRequest request, @StdWho Fruit fruit) {
        // 插入头行
        fruitMapper.insertSelective(fruit);
        // 判断如果行不为空，则迭代循环插入

        if (fruit.getFruitVendors()!= null) {
            processFruitVendor(fruit);
        }

        return fruit;
    }
    //更新头
    public Fruit updateFruit(IRequest request, @StdWho Fruit fruit) {
        fruitMapper.updateByPrimaryKeySelective(fruit);
        // 判断如果行不为空，则迭代循环插入
        if (fruit.getFruitVendors()!= null) {
            processFruitVendor(fruit);
        }

        return fruit;
    }

    private void processFruitVendor(Fruit fruit) {
        for (FruitVendor fruitVendor : fruit.getFruitVendors()) {
            if (fruitVendor.getFruitid() == null) {
                fruitVendor.setFruitid(fruit.getId()); // 设置头ID跟行ID一致
                fruitVendorMapper.insertSelective(fruitVendor);
            } else if (fruitVendor.getId()!= null) {
                fruitVendorMapper.updateByPrimaryKeySelective(fruitVendor);
            }

        }
    }


}
