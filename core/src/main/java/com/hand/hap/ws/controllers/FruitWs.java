package com.hand.hap.ws.controllers;

import com.hand.hap.fruit.dto.Fruit;
import com.hand.hap.fruit.service.IFruitService;
import com.hand.hap.intergration.annotation.HapInbound;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xiaohaozi on 2018/12/15.
 */
@RestController
public class FruitWs extends BaseController {

    @Autowired
    private IFruitService fruitService;


    @RequestMapping(value = "/api/public/com/hand/hap/interface/fruit", method = RequestMethod.POST)
    @ResponseBody
    @HapInbound(apiName = "com.hand.hap.interface.fruit")
    public ResponseData queryWs(HttpServletRequest request,
                                Fruit fruit,
                                @RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return new ResponseData(fruitService.queryList(fruit, page, pageSize));
    }

}
