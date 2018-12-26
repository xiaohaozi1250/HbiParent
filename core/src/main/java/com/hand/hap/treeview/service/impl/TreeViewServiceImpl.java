/**
 * 文件名：TreeViewServiceImpl.java
 * 描述：主数据管理系统
 */
package com.hand.hap.treeview.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hap.treeview.dto.TreeView;
import com.hand.hap.treeview.mapper.TreeViewMapper;
import com.hand.hap.treeview.service.ITreeViewService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 树接口实现类
 *
 * @author [雍廷]
 * @date 2018-2-12 16:35:20
 */
@Service("treeViewService")
public class TreeViewServiceImpl extends BaseServiceImpl<TreeView> implements ITreeViewService {
    /**
     * 获取树节点List
     * 根据treeViews和treeViewNodeParent获取树节点List
     *
     * @param treeViews          树
     * @param treeViewNodeParent 树父节点
     * @return List
     * @date 2018 年 2 月 12 日  下午 16:35:20
     */

    public TreeView getTreeViewNodeList(List<TreeView> treeViews, TreeView treeViewNodeParent) {
        // 节点列表（散列表，用于临时存储节点对象）
        HashMap<Long, TreeView> nodeList = new HashMap<Long, TreeView>();
        //根节点集合
        List<TreeView> treeRoot = new ArrayList<>();

        // 根据结果集构造节点列表（存入散列表）
        for (TreeView treeView : treeViews) {
            nodeList.put(treeView.getId(), treeView);
        }

        //构造无序的多叉树
        Set<?> entrySet = nodeList.entrySet();
        for (Iterator<?> it = entrySet.iterator(); it.hasNext(); ) {
            TreeView node = (TreeView) ((Map.Entry) it.next()).getValue();
            if (node.getParentId() == null || node.getParentId() == -1) {
                node.setExpanded(true);
                treeRoot.add(node);
            } else {

                TreeView treeViewNode = (TreeView) nodeList.get(node.getParentId());
                if (treeViewNode != null) {
                    if (treeViewNode.getItems() == null || treeViewNode.getItems().size() == 0) {
                        List<TreeView> items = new ArrayList<>();
                        items.add(node);
                        treeViewNode.setExpanded(true);
                        treeViewNode.setItems(items);
                    } else {
                        treeViewNode.getItems().add(node);
                    }
                }
            }
        }
        treeViewNodeParent.setItems(treeRoot);

        return treeViewNodeParent;
    }
}
