package com.hand.hap.genstrong.service;

import com.hand.hap.genstrong.dto.GenStrongInfo;

import java.util.List;

/**
 * Created by La on 2018/11/12.
 */
public interface IGenstrongService {

    List<String> showTables();

    int generatorFile(GenStrongInfo info);

    List<String> getModels(GenStrongInfo generatorInfo);

    //获取表字段
    List<String> showColumns(String tableName);
}
