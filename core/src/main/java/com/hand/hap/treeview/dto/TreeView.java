/**
 * 文件名：TreeView.java
 * 描述：主数据管理系统
 */
package com.hand.hap.treeview.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 * 树
 * 构建树实体类
 * @author [雍廷]
 * @date 2018-2-12 16:35:20
 */
public class TreeView implements java.io.Serializable{

    private static final long serialVersionUID = -2078785927442770089L;

    @Id
    @Column
    @GeneratedValue
    private Long id;
    @Column
    private String text;
    @Transient
    private boolean expanded;
    @Column
    private Long parentId;
    @Column
    private String code;
    @Column
    private String icon;
    @Column
    private Long orgTypeId;
    @Column
    private String isEnabled;
    @Column
    private String lang;
    @Column
    private Long mdTypeId;
    @Column
    private Long cateId;
    @Column
    private Long tplId;

    @Transient
    private boolean checked;
    @Transient
    private String type;

    @Transient
    private Long lvl;

    @Transient
    private String widgetType;

    @Transient
    private String dataPrecision;

    @Transient
    private Long dataLength;

    @Transient
    private String dataType;

    @Transient
    private List<TreeView> items;

    public Long getTplId() {
        return tplId;
    }

    public void setTplId(Long tplId) {
        this.tplId = tplId;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public Long getMdTypeId() {
        return mdTypeId;
    }

    public void setMdTypeId(Long mdTypeId) {
        this.mdTypeId = mdTypeId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Long getOrgTypeId() {
        return orgTypeId;
    }
    public void setOrgTypeId(Long orgTypeId) {
        this.orgTypeId = orgTypeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getCode() {
        return code;
    }

    public String getWidgetType() {
        return widgetType;
    }

    public String getDataPrecision() {
        return dataPrecision;
    }

    public void setDataPrecision(String dataPrecision) {
        this.dataPrecision = dataPrecision;
    }

    public void setWidgetType(String widgetType) {
        this.widgetType = widgetType;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<TreeView> getItems() {
        return items;
    }

    public void setItems(List<TreeView> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDataLength() {
        return dataLength;
    }

    public void setDataLength(Long dataLength) {
        this.dataLength = dataLength;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getLvl() {
        return lvl;
    }

    public void setLvl(Long lvl) {
        this.lvl = lvl;
    }

}
