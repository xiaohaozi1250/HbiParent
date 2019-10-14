package com.hand.hap.treeview.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.hr.dto.HrOrgUnit;
import com.hand.hap.hr.service.IOrgUnitService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xiaohaozi on 2019/3/28.
 */

@Controller
public class OrgTreeController extends BaseController {

    @Autowired
    private IOrgUnitService orgUnitService;
    private HrOrgUnit unit;

    @RequestMapping(value = "/tree/orgTree/queryall")
    public ResponseData queryAll(HttpServletRequest request) {
        IRequest requestCtx = this.createRequestContext(request);
        Criteria criteria = new Criteria();
        HrOrgUnit unit = new HrOrgUnit();
        unit.setEnabledFlag("Y");
        return new ResponseData(orgUnitService.selectOptions(requestCtx, unit, criteria));
    }

}


