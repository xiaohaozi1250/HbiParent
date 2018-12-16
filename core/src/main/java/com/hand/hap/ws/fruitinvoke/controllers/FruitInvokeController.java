package com.hand.hap.ws.fruitinvoke.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.fruit.dto.Fruit;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.ws.fruitinvoke.service.FruitInvokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xiaohaozi on 2018/12/15.
 */
@RestController
public class FruitInvokeController extends BaseController{

    @Autowired
    FruitInvokeService service;

    @RequestMapping(value = "/demo/fruit/invoke")
    @ResponseBody
    public ResponseData invoke(Fruit dto, @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        List<Fruit> fruitList = new ArrayList<>();
        Fruit fruit = new Fruit();
        //根据传入的ID，获取数据
        try {
            //这里只是模拟解析，实际入参可根据情况改写
            //调用接口，并获取解析后的数据
            fruit = service.inAction(requestContext);
        } catch (Exception e) {
            System.out.println("invoke Error");
        }
        fruitList.add(fruit);
        //返回请求
        return new ResponseData(fruitList);
    }



}
