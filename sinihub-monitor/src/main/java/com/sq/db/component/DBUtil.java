package com.sq.db.component;

import com.sq.db.domain.ProjectPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
/**
 * Created by ywj on 2015/12/21.
 * JDBC工具类
 */
@Component
public class DBUtil
{
    /**
     * 建立数据库的连接并返回conn
     * @param uri
     * @param userName
     * @param passWord
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConn(String uri,String userName,String passWord) throws ClassNotFoundException,SQLException {
        Connection conn = null;
        Class.forName("com.mysql.jdbc.Driver");
        //获得与数据库的链接
        //jdbc:mysql://服务器IP:端口号/数据库名"
        String url = "jdbc:mysql://"+uri+"?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8";
        conn = DriverManager.getConnection(url,userName,passWord);
        return conn;
    }

//    /**
//     * 关闭数据库
//     * @param rs
//     * @param stmt
//     * @param conn
//     */
//    public static void close(ResultSet rs, Statement stmt, Connection conn){
//        try
//        {
//            if(rs != null){
//                rs.close();
//            }
//        }
//        catch (SQLException e1)
//        {
//            e1.printStackTrace();
//        }finally{
//            try
//            {
//                if(stmt != null){
//                      stmt.close();
//                  }
//            }
//            catch (SQLException e)
//            {
//                e.printStackTrace();
//            }finally{
//                try
//                {
//                    if(conn != null)
//                        conn.close();
//                }
//                catch (SQLException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

//    public static void main(String[] args) {
//        Connection conn = null;
//        String sql;
//        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
//        // 避免中文乱码要指定useUnicode和characterEncoding
//        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
//        // 下面语句之前就要先创建javademo数据库
//        String url = "jdbc:mysql://58.241.142.98:12306/snmis?"
//                + "user=root&password=123456&useUnicode=true&characterEncoding=UTF8";
//
//        try {
//            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
//            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
//            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
//            // or:
//            // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
//            // or：
//            // new com.mysql.jdbc.Driver();
//
//            System.out.println("成功加载MySQL驱动程序");
//            // 一个Connection代表一个数据库连接
//            conn = DriverManager.getConnection(url);
//            System.out.print(conn.isReadOnly());
//            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
//            Statement stmt = conn.createStatement();
//
//        } catch (SQLException e) {
//            System.out.println("MySQL操作错误");
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//        }
//
//    }
}
