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
    public int generatorTables(GenStrongInfo genDemoInfo) {
        int rs = this.service.generatorFile(genDemoInfo);
        return rs;
    }

    @RequestMapping(
            value = {"/getmodels"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public ResponseData getModels(GenStrongInfo genDemoInfo) {
        return new ResponseData(this.service.getModels(genDemoInfo));
    }

/*    @RequestMapping(value = {"/allcolumn"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData showColumns(HttpServletRequest request, @RequestBody String tableName) {
        System.out.println("tableName=" + tableName);
        System.out.println("tableName2+"+this.service.getName(tableName));
        return new ResponseData(this.service.showColumns(tableName));
    }*/

    @RequestMapping(value = {"/allcolumns"}, method = {RequestMethod.POST})
    @ResponseBody
    public int showColumns(GenStrongInfo genDemoInfo) {
        int rs = this.service.showColumns(genDemoInfo);
        return rs;
    }

    @RequestMapping({"/getprojectpath"})
    @ResponseBody
    public String getProjectPath(HttpServletRequest request) {
        //取得根目录路径
        String contextPath = request.getSession().getServletContext().getRealPath("");
        System.out.println(contextPath);
        File cat = new File(contextPath);
        System.out.println(cat.getParent());
        File root = new File(cat.getParent());
        System.out.println(root.getParent());
        String realRath = request.getSession().getServletContext().getRealPath("../");
        System.out.println(realRath);
        System.out.println(System.getProperty("user.dir"));
        System.out.println(getClass().getClassLoader().getResource("/").getPath());
        System.out.println(getClass().getClassLoader().getResource("../").getPath());
        String rootPath = getClass().getResource("./").getFile().toString();
        //当前目录路径
        String currentPath1 = getClass().getResource(".").getFile().toString();
        String currentPath2 = getClass().getResource("").getFile().toString();
        //当前目录的上级目录路径
        String parentPath = getClass().getResource("../").getFile().toString();
        return root.getParent();
    }
}
