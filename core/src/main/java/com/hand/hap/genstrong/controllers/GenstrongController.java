package com.hand.hap.genstrong.controllers;

import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.genstrong.dto.GenStrongInfo;
import com.hand.hap.genstrong.service.IGenstrongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by La on 2018/11/12.
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
    public ResponseData showTables() {
        return new ResponseData(this.service.showTables());
    }

    @RequestMapping({"/newtables"})
    @ResponseBody
    public int generatorTables(GenStrongInfo genStrongInfo) {
        int rs = this.service.generatorFile(genStrongInfo);
        return rs;
    }

    @RequestMapping(
            value = {"/getmodels"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public ResponseData getModels(GenStrongInfo genStrongInfo) {
        System.out.println("genStrongInfo.getPackagePath=" + genStrongInfo.getPackagePath());
        System.out.println("genStrongInfo.getProjectPath=" + genStrongInfo.getProjectPath());
        return new ResponseData(this.service.getModels(genStrongInfo));
    }

}
