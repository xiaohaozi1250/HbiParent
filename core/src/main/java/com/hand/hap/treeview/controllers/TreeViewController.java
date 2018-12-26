/**
 * 文件名：TreeViewController.java
 * 描述：主数据管理系统
 */
package com.hand.hap.treeview.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.treeview.dto.TreeView;
import com.hand.hap.treeview.service.ITreeViewService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * treeview控制器
 * 将数据封装成树形结构
 * @author [雍廷]
 * @date 2018-2-12 16:35:20
 */
@Controller
@RequestMapping(value="/treeView")
public class TreeViewController  extends BaseController {

}

