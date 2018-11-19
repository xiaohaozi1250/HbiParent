package com.hand.hap.fruit.controllers;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.hap.core.IRequest;
import com.hand.hap.excel.dto.ColumnInfo;
import com.hand.hap.excel.dto.ExportConfig;
import com.hand.hap.excel.service.IExportService;
import com.hand.hap.fruit.dto.Fruit;
import com.hand.hap.fruit.dto.FruitVendor;
import com.hand.hap.fruit.service.IFruitService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by zhihao.dai on 2017/4/21.
 */
@RestController
@RequestMapping(path = "/demo/fruit")
public class FruitController extends BaseController{
    @Autowired
    private IFruitService fruitService;
    @Autowired
    IExportService excelService;

    @Autowired
    ObjectMapper objectMapper;


    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData query(HttpServletRequest request,
                              Fruit fruit,
                              @RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int pageSize) {
        //return new ResponseData(fruitService.select(createRequestContext(request),fruit,page,pageSize));
        return new ResponseData(fruitService.queryList(fruit,page,pageSize));
    }


    @RequestMapping(value = "line/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData linequery(HttpServletRequest request,
                                   FruitVendor fruitVendor,
                                   @RequestParam(required = false, defaultValue = "1") int page,
                                   @RequestParam(required = false, defaultValue = "10") int pageSize) {
        //return new ResponseData(fruitService.select(createRequestContext(request),fruit,page,pageSize));
        return new ResponseData(fruitService.queryLineList(fruitVendor,page,pageSize));
    }

    @RequestMapping(path = "/delete")
    public ResponseData delete(HttpServletRequest request,
                               @RequestBody List<Fruit> fruits) {
        //fruitService.batchDelete(fruits);
        fruitService.deleteList(fruits);
        return new ResponseData(true);
    }

    @RequestMapping(path = "/update")
    public ResponseData update(HttpServletRequest request,
                               @RequestBody List<Fruit> fruits) {
        //fruitService.updateByPrimaryKey(createRequestContext(request),fruit);
        fruitService.updateList(fruits);
        return new ResponseData(true);

    }

    @RequestMapping(path = "/insert")
    public ResponseData insert(HttpServletRequest request,
                               @RequestBody Fruit fruit) {
        fruitService.insertSelective(createRequestContext(request),fruit);
        return new ResponseData(true);
    }

    @RequestMapping(path = "/submit")
    public ResponseData batchUpdate(HttpServletRequest request,
                                    @RequestBody List<Fruit> fruits) {
        fruitService.batchUpdate(createRequestContext(request), fruits);
        return new ResponseData(true);
    }


    @RequestMapping(path = "line/submit")
    public ResponseData batchLineUpdate(HttpServletRequest request,
                                    @RequestBody List<Fruit> fruits) {
        List<Fruit> datas  = fruitService.batchLineUpdate(createRequestContext(request), fruits);
        return new ResponseData(datas);
    }

    @RequestMapping(value = "/export")
    public void createFruitXLS(HttpServletRequest request, @RequestParam String config,
                               HttpServletResponse httpServletResponse) throws IOException {
        System.out.println("-------------/sys/fruit/export-----------------");
        IRequest requestContext = createRequestContext(request);
        JavaType type = objectMapper.getTypeFactory().constructParametrizedType(ExportConfig.class,
                ExportConfig.class, Fruit.class, ColumnInfo.class);
        ExportConfig<Fruit, ColumnInfo> exportConfig = objectMapper.readValue(config, type);
        excelService.exportAndDownloadExcel("com.hand.hap.fruit.mapper.FruitMapper.myQuery",
                exportConfig, request, httpServletResponse, requestContext);

    }
}
