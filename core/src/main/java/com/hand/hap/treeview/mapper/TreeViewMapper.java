/**
 * 文件名：TreeViewMapper.java
 * 描述：主数据管理系统
 */
package com.hand.hap.treeview.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.treeview.dto.MdViewAndAttrTpl;
import com.hand.hap.treeview.dto.TreeView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 树mapper
 * 封装树类型结构mapper
 * @author [雍廷]
 * @date 2018-2-12 16:35:20
 */
public interface TreeViewMapper extends Mapper<TreeView> {


}
