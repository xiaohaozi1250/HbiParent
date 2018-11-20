package com.hand.hap.genstrong.service.impl;


import com.hand.hap.mybatis.util.StringUtil;
import freemarker.template.TemplateException;
import com.hand.hap.genstrong.dto.DBColumn;
import com.hand.hap.genstrong.dto.DBTable;
import com.hand.hap.genstrong.dto.GenStrongInfo;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hand.hap.genstrong.service.IGenstrongService;
import org.springframework.stereotype.Service;

/**
 * Created by La on 2018/11/12.
 */
@Service
public class GenstrongServiceImpl implements IGenstrongService {

    @Autowired
    @Qualifier("sqlSessionFactory")
    SqlSessionFactory sqlSessionFactory;

    private Logger logger = LoggerFactory.getLogger(getClass());


    public List<String> getModels(GenStrongInfo generatorInfo) {
        List<String> models = new ArrayList<>();
        models = FileUtil.getModelList(generatorInfo);
        for (String model : models) {
            System.out.println("modelText=>" + model);
        }
        return models;
    }

    public List<String> showTables() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            List<String> tables;

            Connection conn = DBUtil.getConnectionBySqlSession(sqlSession);
            tables = DBUtil.showAllTables(conn);
            conn.close();
            return tables;
        } catch (SQLException e) {
            logger.error("数据库查询出错");
        }
        return new ArrayList<String>();
    }


    public int generatorFile(GenStrongInfo info) {
        int rs = 0;
        String headerTableName = info.getHeaderTargetName();
        DBTable dbHeaderTable = getTableInfo(headerTableName);
        String lineTableName = info.getLineTargetName();
        DBTable dbLineTable = getTableInfo(lineTableName);
        //FileUtil.generatorInfo = info;
        try {
            rs = createFile(dbHeaderTable, dbLineTable, info);
        } catch (IOException e) {
            rs = -1;
            logger.error(e.getMessage());
        } catch (TemplateException e) {
            rs = -1;
            logger.error(e.getMessage());
        }

        return rs;
    }

    // 获取table信息
    public DBTable getTableInfo(String tableName) {
        Connection conn = null;
        DBTable dbTable = new DBTable();
        List<DBColumn> columns = dbTable.getColumns();
        List<String> multiColumns = null;
        List<String> NotNullColumns = null;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            // 设置tablename
            dbTable.setName(tableName);
            conn = DBUtil.getConnectionBySqlSession(sqlSession);
            DatabaseMetaData dbmd = conn.getMetaData();
            // 是否为多语言表
            boolean multiLanguage = DBUtil.isMultiLanguageTable(tableName);
            if (multiLanguage) {
                dbTable.setMultiLanguage(multiLanguage);
                multiColumns = DBUtil.isMultiLanguageColumn(tableName, dbmd);
                // 判断多语言字段
            }
            // 获取主键字段
            String columnPk = DBUtil.getPrimaryKey(tableName, dbmd);
            // 获取不为空的字段
            NotNullColumns = DBUtil.getNotNullColumn(tableName, dbmd);
            // 获取表列信息
            ResultSet rs1 = DBUtil.getTableColumnInfo(tableName, dbmd);

            while (rs1.next()) {
                String columnName = rs1.getString("COLUMN_NAME");
                if ("OBJECT_VERSION_NUMBER".equalsIgnoreCase(columnName) || "REQUEST_ID".equalsIgnoreCase(columnName)
                        || "PROGRAM_ID".equalsIgnoreCase(columnName) || "CREATED_BY".equalsIgnoreCase(columnName)
                        || "CREATION_DATE".equalsIgnoreCase(columnName) || "LAST_UPDATED_BY".equalsIgnoreCase(columnName)
                        || "LAST_UPDATE_DATE".equalsIgnoreCase(columnName) || "LAST_UPDATE_LOGIN".equalsIgnoreCase(columnName)
                        || columnName.toUpperCase().startsWith("ATTRIBUTE")) {
                    continue;
                }
                columns.add(setColumnInfo(rs1, columnPk, NotNullColumns, multiLanguage, multiColumns));
            }
            // 是否是多语言表
            rs1.close();
            conn.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return dbTable;
    }

    private DBColumn setColumnInfo(ResultSet rs1, String columnPk, List<String> NotNullColumns, boolean multiLanguage, List<String> multiColumns) throws SQLException {
        DBColumn column = new DBColumn();
        String columnName = rs1.getString("COLUMN_NAME");
        column.setName(columnName);
        String typeName = rs1.getString("TYPE_NAME");
        column.setJdbcType(typeName);
        if (StringUtil.isNotEmpty(rs1.getString("REMARKS"))) {
            column.setRemarks(rs1.getString("REMARKS"));
        }
        // 判断是否为主键
        if (columnName.equalsIgnoreCase(columnPk)) {
            column.setId(true);
        }
        // 判断是否为null字段
        for (String n : NotNullColumns) {
            if (columnName.equalsIgnoreCase(n) && !columnName.equalsIgnoreCase(columnPk)) {
                if ("BIGINT".equalsIgnoreCase(typeName)) {
                    column.setNotNull(true);
                } else if ("VARCHAR".equalsIgnoreCase(typeName)) {
                    column.setNotEmpty(true);
                }
            }
        }
        // 判断多语言表中的多语言字段
        if (multiLanguage) {
            for (String m : multiColumns) {
                if (m.equals(columnName)) {
                    column.setMultiLanguage(true);
                    break;
                }
            }
        }
        column.setColumnLength(rs1.getString("COLUMN_SIZE"));
        return column;
    }

    public int createFile(DBTable hTable, DBTable lTable, GenStrongInfo info) throws IOException, TemplateException {

        int rs = FileUtil.isFileExist(info);
        if (rs == 0) {
            if (!"NotOperation".equalsIgnoreCase(info.getHeaderDtoStatus())) {
                FileUtil.createDto(hTable, info, "header");
            }
            if (!"NotOperation".equalsIgnoreCase(info.getHeaderControllerStatus())) {
                FileUtil.createFtlInfoByType(FileUtil.pType.Controller, hTable, lTable, info, "header");
            }
            if (!"NotOperation".equalsIgnoreCase(info.getHeaderMapperStatus())) {
                FileUtil.createFtlInfoByType(FileUtil.pType.Mapper, hTable, lTable, info, "header");
            }
            if (!"NotOperation".equalsIgnoreCase(info.getHeaderImplStatus())) {
                FileUtil.createFtlInfoByType(FileUtil.pType.Impl, hTable, lTable, info, "header");
            }
            if (!"NotOperation".equalsIgnoreCase(info.getHeaderServiceStatus())) {
                FileUtil.createFtlInfoByType(FileUtil.pType.Service, hTable, lTable, info, "header");
            }
            if (!"NotOperation".equalsIgnoreCase(info.getHeaderMapperXmlStatus())) {
                FileUtil.createFtlInfoByType(FileUtil.pType.MapperXml, hTable, lTable, info, "header");
            }
            if (!"NotOperation".equalsIgnoreCase(info.getHeaderHtmlStatus())) {
                FileUtil.createFtlInfoByType(FileUtil.pType.Html, hTable, lTable, info, "header");
            }
            if (!"NotOperation".equalsIgnoreCase(info.getLineDtoStatus())) {
                FileUtil.createDto(lTable, info, "line");
            }
            if (!"NotOperation".equalsIgnoreCase(info.getLineMapperStatus())) {
                FileUtil.createFtlInfoByType(FileUtil.pType.Mapper, hTable, lTable, info, "line");
            }
            if (!"NotOperation".equalsIgnoreCase(info.getLineMapperXmlStatus())) {
                FileUtil.createFtlInfoByType(FileUtil.pType.MapperXml, hTable, lTable, info, "line");
            }
            if (!"NotOperation".equalsIgnoreCase(info.getLineImplStatus())) {
                FileUtil.createFtlInfoByType(FileUtil.pType.Impl, hTable, lTable, info, "line");
            }
            if (!"NotOperation".equalsIgnoreCase(info.getLineServiceStatus())) {
                FileUtil.createFtlInfoByType(FileUtil.pType.Service, hTable, lTable, info, "line");
            }
            if (!"NotOperation".equalsIgnoreCase(info.getLineHtmlStatus())) {
                FileUtil.createFtlInfoByType(FileUtil.pType.Html, hTable, lTable, info, "line");
            }
        }

        return rs;
    }

    //获取表字段
    public List<String> showColumns(String tableName) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            List<String> columns;
            Connection conn = DBUtil.getConnectionBySqlSession(sqlSession);
            columns = DBUtil.showAllColumns(conn, tableName);
            conn.close();
            return columns;
        } catch (SQLException e) {
            logger.error("数据库查询出错");
        }
        return new ArrayList<String>();
    }
}
