package com.hand.hap.genstrong.controllers;

import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.genstrong.dto.GenStrongInfo;
import com.hand.hap.genstrong.service.IGenstrongService;
import jodd.io.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Val.Zhang on 2018/11/12.
 */

@Controller
@RequestMapping({"/genstrong"})
public class GenstrongController extends BaseController {
    @Autowired
    IGenstrongService service;

    public GenstrongController() {
    }


    @RequestMapping(
            value = {"/alltables"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    //获取所有表
    public ResponseData showTables() {
        return new ResponseData(this.service.showTables());
    }

    @RequestMapping({"/newtables"})
    @ResponseBody
    //创建输出文件
    public int generatorTables(GenStrongInfo genDemoInfo) {
        int rs = this.service.generatorFile(genDemoInfo);
        return rs;
    }

    @RequestMapping(
            value = {"/getmodels"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    //获取html模板名称
    public ResponseData getModels(GenStrongInfo genDemoInfo) {
        return new ResponseData(this.service.getModels(genDemoInfo));
    }

    @RequestMapping(value = {"/validatecolumn"}, method = {RequestMethod.POST})
    @ResponseBody
    //验证字段是否在表内
    public int showColumns(GenStrongInfo genDemoInfo) {
        int rs = this.service.showColumns(genDemoInfo);
        return rs;
    }

    @RequestMapping({"/getprojectpath"})
    @ResponseBody
    //获取对应的表字段
    public String getProjectPath(HttpServletRequest request) {
        //取得根编译目录路径
        String contextPath = request.getSession().getServletContext().getRealPath("");
        return this.service.getPorjectPath(contextPath);
    }
}
