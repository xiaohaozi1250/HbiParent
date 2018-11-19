package com.hand.hap.genstrong.dto;

import com.hand.hap.genstrong.service.impl.XmlColumnsInfo;

import java.util.List;

/**
 * Created by La on 2018/11/12.
 */
public class FtlInfo {
    private String fileName;
    private String packageName;
    private List<String> importName;
    private List<XmlColumnsInfo> columnsInfoHeader;
    private List<XmlColumnsInfo> columnsInfoLine;
    private String dir;
    private String projectPath;
    private String htmlModelName;

    public FtlInfo() {
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<String> getImportName() {
        return this.importName;
    }

    public void setImportName(List<String> importName) {
        this.importName = importName;
    }

    public String getDir() {
        return this.dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getProjectPath() {
        return this.projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getHtmlModelName() {
        return this.htmlModelName;
    }

    public void setHtmlModelName(String htmlModelName) {
        this.htmlModelName = htmlModelName;
    }

    public List<XmlColumnsInfo> getColumnsInfoHeader() {
        return columnsInfoHeader;
    }

    public void setColumnsInfoHeader(List<XmlColumnsInfo> columnsInfoHeader) {
        this.columnsInfoHeader = columnsInfoHeader;
    }

    public List<XmlColumnsInfo> getColumnsInfoLine() {
        return columnsInfoLine;
    }

    public void setColumnsInfoLine(List<XmlColumnsInfo> columnsInfoLine) {
        this.columnsInfoLine = columnsInfoLine;
    }
}
