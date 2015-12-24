package com.sq.jdbc;

import com.sq.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * ODBC-JDBC连接器.
 * User: shuiqing
 * Date: 2015/5/12
 * Time: 10:18
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class DblinkConnecter {

    private static final Logger log = LoggerFactory.getLogger(DblinkConnecter.class);

    public static Connection connSqlserver (int driverType,String url,String userName, String password) {
        Connection connection = null;
        try {
            log.debug("JDBC->开始链接。。。。。。。。");
            switch(driverType) {
                case Constants.JDBC_DRIVER_SQLSERVER:
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    break;
                case Constants.JDBC_DRIVER_MYSQL:
                    Class.forName("com.mysql.jdbc.Driver");
                    break;
                default:
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            }
            connection = DriverManager.getConnection(url,userName,password);
        } catch (Exception e) {
            log.error("JDBC连接失败.", e);
        }
        return connection;
    }

    /**
     * 释放连接
     * @param conn
     */
    private static void freeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            log.error("freeConnection error!",e);
        }
    }

    /**
     * 释放statement
     * @param statement
     */
    private static void freeStatement(Statement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            log.error("freeStatement error!" ,e);
        }
    }

    /**
     * 释放resultset
     * @param rs
     */
    private static void freeResultSet(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
            log.error("freeResultSet error!",e);
        }
    }

    /**
     * 释放资源
     *
     * @param conn
     * @param statement
     * @param rs
     */
    public static void free(Connection conn, Statement statement, ResultSet rs) {
        if (rs != null) {
            freeResultSet(rs);
        }
        if (statement != null) {
            freeStatement(statement);
        }
        if (conn != null) {
            freeConnection(conn);
        }
    }

    public static void main(String[] args) {
        DblinkConnecter.connSqlserver(2,"jdbc:mysql://183.207.173.194:12306/gov_rd_new?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8",
                "root","123456");
    }
}
