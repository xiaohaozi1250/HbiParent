package com.hand.hap.ws.fruitinvoke.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.fruit.dto.Fruit;
import com.hand.hap.intergration.dto.HapInterfaceHeader;
import com.hand.hap.intergration.service.IHapApiService;
import com.hand.hap.intergration.service.IHapInterfaceHeaderService;
import com.hand.hap.ws.fruitinvoke.utl.JsonDateValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created by xiaohaozi on 2018/12/16.
 */
@Service
public class FruitInvokeService {

    @Autowired
    private IHapInterfaceHeaderService headerService;
    @Resource(name = "restBean")
    private IHapApiService restService;

    //事务回滚
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Fruit inAction(IRequest request) throws Exception {
        Fruit fruit = new Fruit();
        JSONObject Response_Json;
        JSONObject jsonObj;
        JSONArray jsonArray = new JSONArray();
        //实际请求报文内容可自行拼接，或其他方式
        String string = "{\"id\": 1}";
        try {
            JsonConfig jsonConfig = new JsonConfig();
            //注册时间字段处理器
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            //将数据转换成json格式，并讲json日期格式利用时间字段处理器转换成指定格式
            jsonObj = JSONObject.fromObject(string, jsonConfig);
            HapInterfaceHeader hapInterfaceHeader = this.headerService.getHeaderAndLine("RestApiEmpDemo", "REST1");
            //返回报文
            Response_Json = this.restService.invoke(hapInterfaceHeader, jsonObj);
            //获取实际数据、返回状态可单独解析
            jsonArray = Response_Json.getJSONArray("rows");
            //循环数组，获取解析数据
            for (int i = 0; i < jsonArray.size(); i++) {
                fruit.setId(jsonArray.getJSONObject(i).getLong("id"));
                fruit.setName(jsonArray.getJSONObject(i).getString("name"));
                //日期格式先转换成String，然后再存入
                String date = jsonArray.getJSONObject(i).get("activeDate").toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                fruit.setActiveDate(sdf.parse(date));
            }
        } catch (Exception e) {
            throw e;
        }
        return fruit;
    }


}
