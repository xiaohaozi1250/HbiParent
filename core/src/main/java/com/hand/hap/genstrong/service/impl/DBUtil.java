package com.hand.hap.genstrong.service.impl;

import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Val.Zhang on 2018/11/12.
 */
public class DBUtil {
    private static final String COLUMN_NAME = "COLUMN_NAME";

    private DBUtil() {
    }

    public static Connection getConnectionBySqlSession(SqlSession sqlSession) throws SQLException {
        return sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
    }
    //获取所有表
    public static List<String> getAllTables(Connection conn) throws SQLException {
        ArrayList tables = new ArrayList();
        DatabaseMetaData dbmd = conn.getMetaData();
        String database = conn.getCatalog();
        ResultSet rs = dbmd.getTables(database, null, null, new String[]{"TABLE"});
       //将表存入数组集合中
        while(rs.next()) {
            tables.add(rs.getString("TABLE_NAME"));
        }

        return tables;
    }

    public static ResultSet getTableColumnInfo(String table, DatabaseMetaData dbmd) throws SQLException {
        return dbmd.getColumns(null, null, table, null);
    }

    public static boolean isMultiLanguageTable(String table) throws SQLException {
        boolean result = false;
        if(table.toUpperCase().endsWith("_B")) {
            result = true;
        }

        return result;
    }

    public static List<String> getNotNullColumn(String table, DatabaseMetaData dbmd) throws SQLException {
        ArrayList result = new ArrayList();
        ResultSet rs = dbmd.getColumns(null, null, table, null);

        while(rs.next()) {
            if("NO".equalsIgnoreCase(rs.getString("IS_NULLABLE"))) {
                result.add(rs.getString("COLUMN_NAME"));
            }
        }

        return result;
    }

    public static List<String> isMultiLanguageColumn(String table, DatabaseMetaData dbmd) throws SQLException {
        ArrayList result = new ArrayList();
        String tlTable = table.substring(0, table.length() - 2) + "_tl";
        ResultSet rs = getTableColumnInfo(tlTable, dbmd);
        boolean key = false;

        while(rs.next()) {
            String columnName = rs.getString("COLUMN_NAME");
            if(!"OBJECT_VERSION_NUMBER".equalsIgnoreCase(columnName) && !"REQUEST_ID".equalsIgnoreCase(columnName) && !"PROGRAM_ID".equalsIgnoreCase(columnName) && !"CREATED_BY".equalsIgnoreCase(columnName) && !"CREATION_DATE".equalsIgnoreCase(columnName) && !"LAST_UPDATED_BY".equalsIgnoreCase(columnName) && !"LAST_UPDATE_DATE".equalsIgnoreCase(columnName) && !"LAST_UPDATE_LOGIN".equalsIgnoreCase(columnName) && !columnName.toUpperCase().startsWith("ATTRIBUTE")) {
                if(key) {
                    result.add(rs.getString("COLUMN_NAME"));
                }

                if("LANG".equalsIgnoreCase(rs.getString("COLUMN_NAME"))) {
                    key = true;
                }
            }
        }

        return result;
    }

    public static String getPrimaryKey(String table, DatabaseMetaData dbmd) throws SQLException {
        String columnPK = null;

        for(ResultSet rs = dbmd.getPrimaryKeys(null, null, table); rs.next(); columnPK = rs.getString("COLUMN_NAME")) {
        }

        return columnPK;
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if(connection != null) {
            connection.close();
        }

    }

    public static void closeResultSet(ResultSet resultSet) throws SQLException {
        if(resultSet != null) {
            resultSet.close();
        }

    }

    public static void closeSqlSession(SqlSession sqlSession) throws SQLException {
        if(sqlSession != null) {
            sqlSession.close();
        }

    }

    public static String camel2Underline(String line) {
        if(line != null && !"".equals(line)) {
            line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
            StringBuffer sb = new StringBuffer();
            Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
            Matcher matcher = pattern.matcher(line);

            while(matcher.find()) {
                String word = matcher.group();
                sb.append(word.toUpperCase());
                sb.append(matcher.end() == line.length()?"":"_");
            }

            return sb.toString();
        } else {
            return "";
        }
    }
    //获取指定表内的字段
    public static List<String> getllColumns(Connection conn, String tableName) throws SQLException {
        ArrayList columns = new ArrayList();
        DatabaseMetaData dbmd = conn.getMetaData();
        ResultSet rs = DBUtil.getTableColumnInfo(tableName, dbmd);
        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME");
/*            if ("OBJECT_VERSION_NUMBER".equalsIgnoreCase(columnName) || "REQUEST_ID".equalsIgnoreCase(columnName)
                    || "PROGRAM_ID".equalsIgnoreCase(columnName) || "CREATED_BY".equalsIgnoreCase(columnName)
                    || "CREATION_DATE".equalsIgnoreCase(columnName) || "LAST_UPDATED_BY".equalsIgnoreCase(columnName)
                    || "LAST_UPDATE_DATE".equalsIgnoreCase(columnName) || "LAST_UPDATE_LOGIN".equalsIgnoreCase(columnName)) {
                continue;
            }*/
            columns.add(rs.getString("COLUMN_NAME").toLowerCase());
        }
        return columns;
    }
}
