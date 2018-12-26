/**
 * 文件名：ITreeViewService.java
 * 描述：主数据管理系统
 */
package com.hand.hap.treeview.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

import com.hand.hap.treeview.dto.TreeView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 树接口
 *
 * @author [雍廷]
 * @date 2018-2-12 16:35:20
 */
public interface ITreeViewService extends IBaseService<TreeView>, ProxySelf<ITreeViewService> {
    /**
     * 获取树节点List
     * 根据treeViews和treeViewNodeParent获取树节点List
     *
     * @param treeViews          树
     * @param treeViewNodeParent 树父节点
     * @return List
     * @date 2018 年 2 月 12 日  下午 16:35:20
     */
    TreeView getTreeViewNodeList(List<TreeView> treeViews, TreeView treeViewNodeParent);
}
