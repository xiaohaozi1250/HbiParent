package com.hand.hap.master_distributed_execution.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.master_distributed_execution.dto.MdType;
import com.hand.hap.treeview.dto.TreeView;

import java.util.List;

public interface IMdTypeService extends IBaseService<MdType>, ProxySelf<IMdTypeService> {
    List<TreeView> selectMdTypeTree();
}