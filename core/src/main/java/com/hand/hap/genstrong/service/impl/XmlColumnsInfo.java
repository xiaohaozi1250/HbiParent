package com.hand.hap.genstrong.service.impl;

/**
 * Created by Val.Zhang on 2018/11/12.
 */
public class XmlColumnsInfo {
    private String tableColumnsName;
    private String dBColumnsName;
    private String jdbcType;

    public XmlColumnsInfo() {
    }

    public String getTableColumnsName() {
        return this.tableColumnsName;
    }

    public void setTableColumnsName(String tableColumnsName) {
        this.tableColumnsName = tableColumnsName;
    }

    public String getdBColumnsName() {
        return this.dBColumnsName;
    }

    public void setdBColumnsName(String dBColumnsName) {
        this.dBColumnsName = dBColumnsName;
    }

    public String getJdbcType() {
        return this.jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }
}
