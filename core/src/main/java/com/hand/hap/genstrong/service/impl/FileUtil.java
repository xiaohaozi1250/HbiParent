package com.hand.hap.genstrong.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.util.StringUtil;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import com.hand.hap.genstrong.dto.DBColumn;
import com.hand.hap.genstrong.dto.DBTable;
import com.hand.hap.genstrong.dto.FtlInfo;
import com.hand.hap.genstrong.dto.GenStrongInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Val.Zhang on 2018/11/12.
 */
public class FileUtil {
    private static List<String> allClassFiles = new ArrayList();
    private static List<String> allXmlFiles = new ArrayList();
    private static List<String> allHtmlFiles = new ArrayList();

    private FileUtil() {
    }

    public static String columnToCamel(String str) {
        Pattern linePattern = Pattern.compile("_(\\w)");
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }

        matcher.appendTail(sb);
        String s = sb.toString();
        return s;
    }

    public static String camelToColumn(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    public static void createDto(DBTable table, GenStrongInfo generatorInfo, String source) throws IOException {
        boolean needUtil = false;
        boolean needNotNull = false;
        boolean needNotEmpty = false;
        String projectPath = generatorInfo.getProjectPath();
        String parentPackagePath = generatorInfo.getParentPackagePath();
        String packagePath = generatorInfo.getPackagePath();
        String directory;
        String name;
        //获取目录和文件名
        if (source.equals("header")) {
            name = generatorInfo.getHeaderDtoName().substring(0, generatorInfo.getHeaderDtoName().indexOf("."));
            directory = projectPath + "/src/main/java/" + parentPackagePath + "/" + packagePath + "/dto/" + generatorInfo.getHeaderDtoName();
        } else {
            name = generatorInfo.getLineDtoName().substring(0, generatorInfo.getLineDtoName().indexOf("."));
            directory = projectPath + "/src/main/java/" + parentPackagePath + "/" + packagePath + "/dto/" + generatorInfo.getLineDtoName();
        }
        File file = new File(directory);
        //创建文件
        createFileDir(file);
        file.createNewFile();
        //获取表字段
        List columns = table.getColumns();
        Iterator sb = columns.iterator();
        //分析表字段
        String d;
        while (sb.hasNext()) {
            DBColumn dir1 = (DBColumn) sb.next();
            d = dir1.getJdbcType().toUpperCase();
            byte p = -1;
            switch (d.hashCode()) {
                case -2034720975:
                    if (d.equals("DECIMAL")) {
                        p = 7;
                    }
                    break;
                case -1981034679:
                    if (d.equals("NUMBER")) {
                        p = 10;
                    }
                    break;
                case -1718637701:
                    if (d.equals("DATETIME")) {
                        p = 2;
                    }
                    break;
                case -1618932450:
                    if (d.equals("INTEGER")) {
                        p = 6;
                    }
                    break;
                case -1453246218:
                    if (d.equals("TIMESTAMP")) {
                        p = 1;
                    }
                    break;
                case -594415409:
                    if (d.equals("TINYINT")) {
                        p = 8;
                    }
                    break;
                case 72655:
                    if (d.equals("INT")) {
                        p = 5;
                    }
                    break;
                case 2090926:
                    if (d.equals("DATE")) {
                        p = 0;
                    }
                    break;
                case 2575053:
                    if (d.equals("TIME")) {
                        p = 3;
                    }
                    break;
                case 66988604:
                    if (d.equals("FLOAT")) {
                        p = 11;
                    }
                    break;
                case 1959128815:
                    if (d.equals("BIGINT")) {
                        p = 4;
                    }
                    break;
                case 2022338513:
                    if (d.equals("DOUBLE")) {
                        p = 9;
                    }
            }

            switch (p) {
                case 0:
                case 1:
                case 2:
                case 3:
                    dir1.setJavaType("Date");
                    dir1.setJdbcType("DATE");
                    needUtil = true;
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    dir1.setJavaType("Long");
                    dir1.setJdbcType("DECIMAL");
                    break;
                case 9:
                    dir1.setJavaType("Double");
                    dir1.setJdbcType("DECIMAL");
                    break;
                case 10:
                case 11:
                    dir1.setJavaType("Float");
                    dir1.setJdbcType("DECIMAL");
                    break;
                default:
                    dir1.setJavaType("String");
                    dir1.setJdbcType("VARCHAR");
            }

            if (dir1.isNotNull()) {
                needNotNull = true;
            } else if (dir1.isNotEmpty()) {
                needNotEmpty = true;
            }
        }
        //拼接导入包和字段方法
        StringBuilder sb1 = new StringBuilder();
        String dir11 = parentPackagePath + "/" + packagePath + "/dto";
        dir11 = dir11.replaceAll("/", ".");
        sb1.append("package " + dir11 + ";\r\n\r\n");
        sb1.append("/**Auto Generated By Hap Code Generator**/\r\n");
        sb1.append("import javax.persistence.GeneratedValue;\r\n");
        sb1.append("import javax.persistence.Id;\r\n");
        sb1.append("import com.hand.hap.mybatis.annotation.ExtensionAttribute;\r\n");
        sb1.append("import org.hibernate.validator.constraints.Length;\r\n");
        sb1.append("import javax.persistence.Table;\r\n");
        d = BaseDTO.class.getName();
        sb1.append("import " + d + ";\r\n");
        if (needUtil) {
            sb1.append("import java.util.Date;\r\n");
        }

        if (needNotNull) {
            sb1.append("import javax.validation.constraints.NotNull;\r\n");
        }

        if (needNotEmpty) {
            sb1.append("import org.hibernate.validator.constraints.NotEmpty;\r\n");
        }

        if (source.equals("header")) {
            sb1.append("import com.hand.hap.core.annotation.Children;\r\n");
            sb1.append("import javax.persistence.Transient;\r\n");
            sb1.append("import java.util.List;\r\n");
        }
        if (table.isMultiLanguage()) {
            String p1 = MultiLanguageField.class.getName();
            String cl = MultiLanguage.class.getName();
            sb1.append("import " + p1 + ";\r\n");
            sb1.append("import " + cl + ";\r\n");
            sb1.append("\r\n\r\n@MultiLanguage\r\n");
        }

        sb1.append("@ExtensionAttribute(disable=true)\r\n");
        sb1.append("@Table(name = \"" + table.getName() + "\")\r\n");
        sb1.append("public class " + name + " extends BaseDTO {\r\n\r\n");
        Iterator p2 = columns.iterator();

        String name1;
        DBColumn cl1;
        while (p2.hasNext()) {
            cl1 = (DBColumn) p2.next();
            name1 = "     public static final String FIELD_" + cl1.getName().toUpperCase() + " = \"" + columnToCamel(cl1.getName()) + "\";\r\n";
            sb1.append(name1);
        }

        sb1.append("\r\n\r\n");
        p2 = columns.iterator();

        while (p2.hasNext()) {
            cl1 = (DBColumn) p2.next();
            if (cl1.isId()) {
                sb1.append("     @Id\r\n     @GeneratedValue\r\n");
            } else {
                if (cl1.isNotEmpty()) {
                    sb1.append("     @NotEmpty\r\n");
                } else if (cl1.isNotNull()) {
                    sb1.append("     @NotNull\r\n");
                }

                if (cl1.getJavaType().equalsIgnoreCase("String")) {
                    sb1.append("     @Length(max = ");
                    sb1.append(cl1.getColumnLength() + ")\r\n");
                }
            }

            if (cl1.isMultiLanguage()) {
                sb1.append("     @MultiLanguageField\r\n");
            }

            name1 = "     private " + cl1.getJavaType() + " " + columnToCamel(cl1.getName()) + ";";
            if (!StringUtil.isEmpty(cl1.getRemarks())) {
                name1 = name1 + " //" + cl1.getRemarks();
            }

            name1 = name1 + "\r\n\r\n";
            sb1.append(name1);
        }

        sb1.append("\r\n");
        p2 = columns.iterator();

        while (p2.hasNext()) {
            cl1 = (DBColumn) p2.next();
            name1 = columnToCamel(cl1.getName());
            String name2 = name1.substring(0, 1).toUpperCase() + name1.substring(1);
            sb1.append("     public void set" + name2 + "(" + cl1.getJavaType() + " " + name1 + "){\r\n");
            sb1.append("         this." + name1 + " = " + name1 + ";\r\n");
            sb1.append("     }\r\n\r\n");
            sb1.append("     public " + cl1.getJavaType() + " get" + name2 + "(){\r\n");
            sb1.append("         return " + name1 + ";\r\n");
            sb1.append("     }\r\n\r\n");
        }
        //新建行列
        if (source.equals("header")) {
            String lineDtoName = generatorInfo.getLineDtoName().substring(0, generatorInfo.getLineDtoName().indexOf("."));
            sb1.append("     @Children\r\n");
            sb1.append("     @Transient\r\n");
            sb1.append("     private List<" + lineDtoName + "> lines;\r\n");
            sb1.append("     public void setLines" + "(List<" + lineDtoName + "> lines" + "){\r\n");
            sb1.append("         this.lines = lines;\r\n");
            sb1.append("     }\r\n\r\n");
            sb1.append("     public List<" + lineDtoName + "> getLines" + "(){\r\n");
            sb1.append("         return lines;\r\n");
            sb1.append("     }\r\n\r\n");
        }
        sb1.append("     }\r\n\r\n");
        PrintWriter p3 = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
        //写入到刚创建的DTO文件中
        p3.write(sb1.toString());
        p3.close();
    }

    public static void createFtlInfoByType(FileUtil.pType type, DBTable hTbl, DBTable lTbl, GenStrongInfo generatorInfo, String source) throws IOException, TemplateException {
        String projectPath = generatorInfo.getProjectPath();
        String parentPackagePath = generatorInfo.getParentPackagePath();
        String packagePath = generatorInfo.getPackagePath();
        String htmlModelName;
        String htmlName;

        //获取HTML模板名称和html名称
        if (source.equals("header")) {
            htmlModelName = generatorInfo.getHeaderHtmlModelName();
            htmlName = generatorInfo.getHeaderHtmlName();
        } else {
            htmlModelName = generatorInfo.getLineHtmlModelName();
            htmlName = generatorInfo.getLineHtmlName();
        }
        String pac = parentPackagePath + "/" + packagePath;
        FtlInfo info = new FtlInfo();
        String directory = null;
        ArrayList importPackages = new ArrayList();
        //根据不同文件类型拼接对应的导入包
        if (type.equals(pType.Controller) && source.equals("header")) {
            directory = projectPath + "/src/main/java/" + pac + "/controllers/" + generatorInfo.getHeaderControllerName();
            importPackages.add("org.springframework.stereotype.Controller");
            importPackages.add(BaseController.class.getName());
            importPackages.add(IRequest.class.getName());
            importPackages.add(ResponseData.class.getName());
        } else if (type.equals(pType.Mapper)) {
            if (source.equals("header")) {
                directory = projectPath + "/src/main/java/" + pac + "/mapper/" + generatorInfo.getHeaderMapperName();
            } else {
                directory = projectPath + "/src/main/java/" + pac + "/mapper/" + generatorInfo.getLineMapperName();
            }
            importPackages.add("com.hand.hap.mybatis.common.Mapper");
        } else if (type.equals(pType.MapperXml)) {
            if (source.equals("header")) {
                directory = projectPath + "/src/main/resources/" + pac + "/mapper/" + generatorInfo.getHeaderMapperXmlName();
            } else {
                directory = projectPath + "/src/main/resources/" + pac + "/mapper/" + generatorInfo.getLineMapperXmlName();
            }
        } else if (type.equals(pType.Service)) {
            if (source.equals("header")) {
                directory = projectPath + "/src/main/java/" + pac + "/service/" + generatorInfo.getHeaderServiceName();
            } else {
                directory = projectPath + "/src/main/java/" + pac + "/service/" + generatorInfo.getLineServiceName();
            }
            importPackages.add(ProxySelf.class.getName());
            importPackages.add(IBaseService.class.getName());
        } else if (type.equals(pType.Impl)) {
            if (source.equals("header")) {
                directory = projectPath + "/src/main/java/" + pac + "/service/impl/" + generatorInfo.getHeaderImplName();
            } else {
                directory = projectPath + "/src/main/java/" + pac + "/service/impl/" + generatorInfo.getLineImplName();
            }
            importPackages.add(BaseServiceImpl.class.getName());
            importPackages.add("org.springframework.stereotype.Service");
        } else if (type.equals(pType.Html)) {
            directory = projectPath + "/src/main/webapp/WEB-INF/view/" + packagePath + "/" + htmlName;
        }

        pac = pac.replaceAll("/", ".");
        info.setPackageName(pac);
        info.setDir(directory);
        info.setProjectPath(projectPath);
        info.setImportName(importPackages);
        info.setHtmlModelName(htmlModelName);
        List columns = hTbl.getColumns();
        ArrayList columnsInfos = new ArrayList();

        Iterator var13 = columns.iterator();
        XmlColumnsInfo columnsInfo;

        while (var13.hasNext()) {
            columnsInfo = getColumnsinfo(var13);
            columnsInfos.add(columnsInfo);
        }

        info.setColumnsInfoHeader(columnsInfos);

        columns = lTbl.getColumns();
        ArrayList columnsInfosLine = new ArrayList();
        var13 = columns.iterator();

        while (var13.hasNext()) {
            columnsInfo = getColumnsinfo(var13);
            columnsInfosLine.add(columnsInfo);
        }

        info.setColumnsInfoLine(columnsInfosLine);
        //创建文件
        createFtl(info, type, generatorInfo, source, lTbl.getName());
    }

    public static XmlColumnsInfo getColumnsinfo(Iterator var13) {
        XmlColumnsInfo columnsInfo = new XmlColumnsInfo();
        DBColumn column = (DBColumn) var13.next();
        columnsInfo.setTableColumnsName(columnToCamel(column.getName()));
        columnsInfo.setdBColumnsName(column.getName());
        columnsInfo.setJdbcType(column.getJdbcType());
        return columnsInfo;
    }

    public static void createFtl(FtlInfo ftlInfo, FileUtil.pType type, GenStrongInfo generatorInfo, String source, String tableName) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        Template template = null;
        HashMap map = new HashMap();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //模板路径
        cfg.setServletContextForTemplateLoading(request.getServletContext(), "WEB-INF/view/genstrong/ftl");
        //选择模板
        if (type.equals(FileUtil.pType.Controller) && source.equals("header")) {
            template = cfg.getTemplate("controllers.ftl");
        } else if (type.equals(FileUtil.pType.MapperXml)) {
            template = cfg.getTemplate("mapperxml.ftl");
        } else if (type.equals(FileUtil.pType.Mapper)) {
            template = cfg.getTemplate("mapper.ftl");
        } else if (type.equals(FileUtil.pType.Service)) {
            template = cfg.getTemplate("service.ftl");
        } else if (type.equals(FileUtil.pType.Impl)) {
            template = cfg.getTemplate("impl.ftl");
        } else if (type.equals(FileUtil.pType.Html)) {
            template = cfg.getTemplate(ftlInfo.getHtmlModelName());
        }

        if (null != template) {
            template.setEncoding("UTF-8");
            File file = new File(ftlInfo.getDir());
            //创建文件
            createFileDir(file);
            FileOutputStream out = new FileOutputStream(file);
            //获取模板对象数据
            map.put("package", ftlInfo.getPackageName());
            map.put("import", ftlInfo.getImportName());
            map.put("name", ftlInfo.getFileName());
            map.put("headerDtoName", generatorInfo.getHeaderDtoName().substring(0, generatorInfo.getHeaderDtoName().indexOf(46)));
            map.put("lineDtoName", generatorInfo.getLineDtoName().substring(0, generatorInfo.getLineDtoName().indexOf(46)));
            map.put("headerControllerName", generatorInfo.getHeaderControllerName().substring(0, generatorInfo.getHeaderControllerName().indexOf(46)));
            map.put("headerMapperName", generatorInfo.getHeaderMapperName().substring(0, generatorInfo.getHeaderMapperName().indexOf(46)));
            map.put("headerMapperXmlName", generatorInfo.getHeaderMapperXmlName().substring(0, generatorInfo.getHeaderMapperXmlName().indexOf(46)));
            map.put("headerServiceName", generatorInfo.getHeaderServiceName().substring(0, generatorInfo.getHeaderServiceName().indexOf(46)));
            map.put("headerImplName", generatorInfo.getHeaderImplName().substring(0, generatorInfo.getHeaderImplName().indexOf(46)));
            map.put("lineMapperName", generatorInfo.getLineMapperName().substring(0, generatorInfo.getLineMapperName().indexOf(46)));
            map.put("lineMapperXmlName", generatorInfo.getLineMapperXmlName().substring(0, generatorInfo.getLineMapperXmlName().indexOf(46)));
            map.put("lineServiceName", generatorInfo.getLineServiceName().substring(0, generatorInfo.getLineServiceName().indexOf(46)));
            map.put("lineImplName", generatorInfo.getLineImplName().substring(0, generatorInfo.getLineImplName().indexOf(46)));
            map.put("columnsInfoHeader", ftlInfo.getColumnsInfoHeader());
            map.put("columnsInfoLine", ftlInfo.getColumnsInfoLine());
            map.put("tableName", tableName);

            String url = generatorInfo.getHeaderTargetName().toLowerCase();
            String lineUrl = generatorInfo.getLineHtmlName().toLowerCase();
            String packagePath = generatorInfo.getPackagePath().toLowerCase();
            if (url.endsWith("_b")) {
                url = url.substring(0, url.length() - 2);
            }

            url = url.replaceAll("_", "/");
            map.put("headerQueryUrl", "/" + url + "/query");
            map.put("headerSubmitUrl", "/" + url + "/submit");
            map.put("headerRemoveUrl", "/" + url + "/remove");
            map.put("lineUrl", "/" + packagePath + "/" + lineUrl);
            lineUrl = generatorInfo.getLineTargetName().toLowerCase();
            lineUrl = lineUrl.replaceAll("_", "/");
            map.put("lineQueryUrl", "/" + lineUrl + "/query");
            map.put("lineSubmitUrl", "/" + lineUrl + "/submit");
            map.put("lineRemoveUrl", "/" + lineUrl + "/remove");
            if (source.equals("header")) {
                map.put("source", "header");
            } else {
                map.put("source", "line");
            }
            String headerRelationColumn = changeToJavaFiled(generatorInfo.getHeaderRelationColumn());
            map.put("headerRelationColumn", headerRelationColumn);
            String lineRelationColumn = changeToJavaFiled(generatorInfo.getLineRelationColumn());
            map.put("lineColumn", generatorInfo.getLineRelationColumn());
            map.put("lineRelationColumn", lineRelationColumn);
            //创建模板输出
            template.process(map, new OutputStreamWriter(out));
            out.flush();
            out.close();
        }

    }

    public static String changeToJavaFiled(String field) {
        String[] fields = field.split("_");
        StringBuilder sbuilder = new StringBuilder(fields[0]);
        for (int i = 1; i < fields.length; i++) {
            char[] cs = fields[i].toCharArray();
            //if (i == 1) {
            cs[0] -= 32;
/*            } else {

            }*/
            sbuilder.append(String.valueOf(cs));
        }
        return sbuilder.toString();
    }

    public static void createFileDir(File file) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

    }

    //验证文件名是否存在
    public static int isFileExist(GenStrongInfo generatorInfo) {
        byte rs = 0;
        //检索指定路径，去针对不同类型文件去一一对比
        String classDir = generatorInfo.getProjectPath() + "/src/main/java/" + generatorInfo.getParentPackagePath();
        String htmlDir = generatorInfo.getProjectPath() + "/src/main/webapp/WEB-INF/view";
        String xmlDir = generatorInfo.getProjectPath() + "/src/main/resources/" + generatorInfo.getParentPackagePath();

        //判断有没有重复的Dto文件
        //获取项目文件列表
        getFileList(classDir, classDir, generatorInfo);
        for (String name : allClassFiles) {
            if (name.equals(generatorInfo.getHeaderDtoName())) {
                if ("Create".equalsIgnoreCase(generatorInfo.getHeaderDtoStatus())) {
                    rs = 1;
                    break;
                } /*else if ("Cover".equalsIgnoreCase(generatorInfo.getHeaderDtoStatus())) {
                    //状态是“Cover”则判断文件是否存在
                    File file1 = new File(
                            classDir + "/" + generatorInfo.getPackagePath() + "/dto/" + generatorInfo.getHeaderDtoName());
                    if (!file1.exists()) {
                        rs = 1;
                        break;
                    }
                }*/
            }
            if (name.equals(generatorInfo.getLineDtoName())) {
                if ("Create".equalsIgnoreCase(generatorInfo.getLineDtoStatus())) {
                    rs = 1;
                    break;
                }
            }
        }
        //判断有没有重复的Service文件
        if (rs == 0) {
            for (String name : allClassFiles) {
                if (name.equals(generatorInfo.getHeaderServiceName())) {
                    if ("Create".equalsIgnoreCase(generatorInfo.getHeaderServiceStatus())) {
                        rs = 2;
                        break;
                    }
                }
                if (name.equals(generatorInfo.getLineServiceName())) {
                    if ("Create".equalsIgnoreCase(generatorInfo.getLineServiceStatus())) {
                        rs = 2;
                        break;
                    }
                }
            }
        }
        //判断有没有重复的Impl文件
        if (rs == 0) {
            for (String name : allClassFiles) {
                if (name.equals(generatorInfo.getHeaderImplName())) {
                    if ("Create".equalsIgnoreCase(generatorInfo.getHeaderImplStatus())) {
                        rs = 3;
                        break;
                    }
                }
                if (name.equals(generatorInfo.getLineImplName())) {
                    if ("Create".equalsIgnoreCase(generatorInfo.getLineImplStatus())) {
                        rs = 3;
                        break;
                    }
                }
            }
        }
        //判断有没有重复的Controller文件
        if (rs == 0) {
            for (String name : allClassFiles) {
                if (name.equals(generatorInfo.getHeaderControllerName())) {
                    if ("Create".equalsIgnoreCase(generatorInfo.getHeaderControllerStatus())) {
                        rs = 4;
                        break;
                    }
                }
            }
        }
        if (rs == 0) {
            for (String name : allClassFiles) {
                if (name.equals(generatorInfo.getHeaderMapperName())) {
                    if ("Create".equalsIgnoreCase(generatorInfo.getHeaderMapperStatus())) {
                        rs = 5;
                        break;
                    }
                }
                if (name.equals(generatorInfo.getLineMapperName())) {
                    if ("Create".equalsIgnoreCase(generatorInfo.getLineMapperStatus())) {
                        rs = 5;
                        break;
                    }
                }
            }
        }

        // 判断有没有重复的xml文件
        if (rs == 0) {
            getFileList(xmlDir, xmlDir, generatorInfo);
            for (String name : allXmlFiles) {
                if (name.equals(generatorInfo.getHeaderMapperXmlName())) {
                    if ("Create".equalsIgnoreCase(generatorInfo.getHeaderMapperXmlStatus())) {
                        rs = 6;
                        break;
                    }
                }
                if (name.equals(generatorInfo.getLineMapperXmlName())) {
                    if ("Create".equalsIgnoreCase(generatorInfo.getLineMapperXmlStatus())) {
                        rs = 6;
                        break;
                    }
                }
            }
        }
        // 判断有没有重复的html文件
        if (rs == 0) {
            getFileList(htmlDir, htmlDir, generatorInfo);
            for (String name : allHtmlFiles) {
                if (name.equals(generatorInfo.getHeaderHtmlName())) {
                    if ("Create".equalsIgnoreCase(generatorInfo.getHeaderHtmlStatus())) {
                        rs = 7;
                        break;
                    }
                }
                if (name.equals(generatorInfo.getLineHtmlName())) {
                    if ("Create".equalsIgnoreCase(generatorInfo.getLineHtmlStatus())) {
                        rs = 7;
                        break;
                    }
                }
            }
        }
        return rs;
    }

    //获取项目文件列表
    public static void getFileList(String basePath, String directory, GenStrongInfo generatorInfo) {
        File dir = new File(basePath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; ++i) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) {
                    getFileList(files[i].getAbsolutePath(), directory, generatorInfo);
                } else if (directory.equals(generatorInfo.getProjectPath() + "/src/main/java/" + generatorInfo.getParentPackagePath())) {
                    allClassFiles.add(fileName);
                } else if (directory.equals(generatorInfo.getProjectPath() + "/src/main/resources/" + generatorInfo.getParentPackagePath())) {
                    allXmlFiles.add(fileName);
                } else if (directory.equals(generatorInfo.getProjectPath() + "/src/main/webapp/WEB-INF/view")) {
                    allHtmlFiles.add(fileName);
                }
            }
        }
    }

    //获取HTML模板列表
    public static List<String> getModelList(GenStrongInfo generatorInfo) {
        List<String> allModelFiles = new ArrayList();
        String classDir = generatorInfo.getProjectPath() + "/src/main/webapp/WEB-INF/view/genstrong/ftl";
        System.out.println("classDir=" + classDir);
        File dir = new File(classDir);
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; ++i) {
                String fileName = files[i].getName();
                if (fileName.toLowerCase().contains("html")) {
                    System.out.println("fileName=" + fileName);
                    allModelFiles.add(fileName);
                }
            }
        } else {
            System.out.println("files is null");
        }
        return allModelFiles;
    }

    public static enum pType {
        Controller,
        MapperXml,
        Mapper,
        Service,
        Impl,
        Html;

        private pType() {
        }
    }
}

