package com.hand.hap.master_distributed_execution.dto;

/**
 * Auto Generated By Hap Code Generator
 **/

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmlDistributeSituation {

    @XmlElement(name = "headerId")
    private Long headerId; //表ID，主键，供其他表做外键

    @XmlElement(name = "itemCode")
    private String itemCode;

    @XmlElement(name = "name")
    private String name;

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }

    public Long getHeaderId() {
        return headerId;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}