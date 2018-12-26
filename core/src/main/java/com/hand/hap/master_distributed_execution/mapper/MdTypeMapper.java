package com.hand.hap.master_distributed_execution.mapper;


import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.master_distributed_execution.dto.MdType;
import com.hand.hap.treeview.dto.TreeView;

import java.util.List;

public interface MdTypeMapper extends Mapper<MdType>{
    List<TreeView> selectMdTypeTreeData();
}