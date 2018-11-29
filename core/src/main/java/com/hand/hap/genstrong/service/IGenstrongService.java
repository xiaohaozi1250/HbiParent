package com.hand.hap.genstrong.service;

import com.hand.hap.genstrong.dto.GenStrongInfo;

import java.util.List;

/**
 * Created by Val.Zhang on 2018/11/12.
 */
public interface IGenstrongService {

    List<String> showTables();

    int generatorFile(GenStrongInfo info);

    List<String> getModels(GenStrongInfo generatorInfo);

    List<String> showColumns(String tableName);

    String getName(String name);

    int showColumns(GenStrongInfo genDemoInfo);
}
