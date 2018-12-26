/**
 * 文件名：TreeViewType.java
 * 描述：主数据管理系统
 */
package com.hand.hap.treeview.dto;

/**
 * 文件名：TreeViewType.java
 * 描述：主数据管理系统
 */
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * TreeViewType实体类
 * 构建树的类型实体类
 * @author [雍廷]
 * @date 2018-2-12 16:35:20
 */
public class TreeViewType implements java.io.Serializable {

    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column
    private String name;
    @Column
    private String code;
    @Column
    private String isEnabled;

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
