package com.hand.hap.master_distributed_execution.service.impl;

import com.hand.hap.master_distributed_execution.mapper.MdTypeMapper;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hap.treeview.dto.TreeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hap.master_distributed_execution.dto.MdType;
import com.hand.hap.master_distributed_execution.service.IMdTypeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class MdTypeServiceImpl extends BaseServiceImpl<MdType> implements IMdTypeService {
    @Autowired
    private MdTypeMapper mdTypeMapper;

    /**
     * @return
     * @desc 主数据类型树
     * @date 2018-12-26
     */
    public List<TreeView> selectMdTypeTree(Long id) {
        return mdTypeMapper.selectMdTypeTreeData(id);
    }
}