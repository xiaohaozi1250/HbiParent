package com.hand.hap.master_distributed_execution.controllers;

import com.hand.hap.treeview.dto.TreeView;
import com.hand.hap.treeview.service.ITreeViewService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.master_distributed_execution.dto.MdType;
import com.hand.hap.master_distributed_execution.service.IMdTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MdTypeController extends BaseController {

    @Autowired
    private IMdTypeService service;
    @Autowired
    ITreeViewService treeViewService;

    /**
     * @return List<TreeView>
     * @desc 组织类型树
     * @date 2018-2-22
     */
    @RequestMapping(value = "/mdTypeTree", method = RequestMethod.POST)
    @ResponseBody
    public List<TreeView> orgTypeTreeView(@RequestBody Long id) {
        System.out.println("id:" + id);
        List<TreeView> treeViews = new ArrayList<>();
        TreeView treeViewRoot = new TreeView();
        treeViewRoot.setText("主数据类型");
        treeViewRoot.setCode("Master Type");
        treeViewRoot.setExpanded(true);
        treeViewRoot.setParentId(new Long(-1));
        List<TreeView> mdTypeTree = service.selectMdTypeTree(id);
        treeViewService.getTreeViewNodeList(mdTypeTree, treeViewRoot);
        if (treeViewRoot.getItems().size() < 0) {
            return new ArrayList<>();
        }
        treeViews.add(treeViewRoot);
        return treeViews;
    }

}