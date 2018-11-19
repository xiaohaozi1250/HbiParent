package com.hand.hap.relink.dto;

import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/5/13.
 */
@Table(name = "region")
@ExtensionAttribute(disable = true)
public class Link extends BaseDTO {

    @Id
    @GeneratedValue(
            generator = "IDENTITY"
    )

    @Column
    private Double regionId;
    @Column
    @Condition(operator = "LIKE")
    private String regionName;
    @Column
    private Double parentId;
    @Column
    private Double regionLevel;

    public Double getRegionId() {
        return regionId;
    }

    public void setRegionId(Double regionId) {
        this.regionId = regionId;
    }
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Double getParentId() {
        return parentId;
    }

    public void setParentId(Double parentId) {
        this.parentId = parentId;
    }

    public Double getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(Double regionLevel) {
        this.regionLevel = regionLevel;
    }



}
