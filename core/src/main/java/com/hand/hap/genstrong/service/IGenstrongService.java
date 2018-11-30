package com.hand.hap.genstrong.service;

import com.hand.hap.genstrong.dto.GenStrongInfo;

import java.util.List;

/**
 * Created by Val.Zhang on 2018/11/12.
 */
public interface IGenstrongService {
    //获取所有表名
    List<String> showTables();
    //创建文件
    int generatorFile(GenStrongInfo info);
   //获取Html模板名称
    List<String> getModels(GenStrongInfo generatorInfo);
   //验证关联字段是否在表内
    int showColumns(GenStrongInfo genDemoInfo);
    //获取项目路径
    String getPorjectPath(String contextPath);
}
