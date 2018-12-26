/**
 * 文件名：MdViewAndAttrTpl.java
 * 描述：主数据管理系统
 */
package com.hand.hap.treeview.dto;

/**
 * MdViewAndAttrTpl实体类
 * 构建主数据视图和属性模板实体类
 * @author [雍廷]
 * @date 2018-2-12 16:35:20
 */
public class MdViewAndAttrTpl {
    private String tplCode;

    private String tplName;

    private  Long mdTypeCateId;

    private String mdCateName;

    private Long mdTypeId;

    public String getTplCode() {
        return tplCode;
    }

    public void setTplCode(String tplCode) {
        this.tplCode = tplCode;
    }

    public String getTplName() {
        return tplName;
    }

    public String getMdCateName() {
        return mdCateName;
    }

    public void setMdCateName(String mdCateName) {
        this.mdCateName = mdCateName;
    }

    public Long getMdTypeId() {
        return mdTypeId;
    }

    public void setMdTypeId(Long mdTypeId) {
        this.mdTypeId = mdTypeId;
    }

    public void setTplName(String tplName) {
        this.tplName = tplName;
    }

    public Long getMdTypeCateId() {
        return mdTypeCateId;
    }

    public void setMdTypeCateId(Long mdTypeCateId) {
        this.mdTypeCateId = mdTypeCateId;
    }
}
