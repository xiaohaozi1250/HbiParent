package com.hand.hap.relink.controllers;

import com.hand.hap.relink.dto.Link;
import com.hand.hap.relink.service.ILinkService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/5/13.
 */
@RestController
@RequestMapping(path = {"/demo/link","/api/demo/link"})
public class LinkController extends BaseController {
    @Autowired
    private ILinkService linkService;

    @RequestMapping(path = "/query")
    public ResponseData query(HttpServletRequest request,
                              Link link,
                              @RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "100") int pageSize) {
        return new ResponseData(linkService.queryList(link,page,pageSize));
    }
}
