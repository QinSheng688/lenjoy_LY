package com.lenjoy.utils;

import java.sql.*;
import java.util.LinkedList;

public class ConnectionPool {
    //最小连接数
    private static  int  MIN_POOL_NUN;
    //最大连接数
    private static  int MAX_POOL_NUN;
    private static  String USER_NAME;
    private static  String PASSWORD;
    private static  String DRIVER;
    private static  String URL;

    //装链接的容器
    private static LinkedList<Connection> connectionPool=new LinkedList<>();


    static {
        init();
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("加载数据库驱动异常");
            e.printStackTrace();
        }
        //给连接池填充链接(参照最小连接数)
        initConnection();
    }

    //初始化方法，给属性赋值
    private static  void init(){
        //先把jdbc配置文件加载到程序
        PropertiesUtil.load("jdbc");
        //根据配置文件里面的key给连接池中的属性赋值
        DRIVER=PropertiesUtil.getValue("jdbc.driver");
        URL=PropertiesUtil.getValue("jdbc.url");
        USER_NAME=PropertiesUtil.getValue("jdbc.userName");
        PASSWORD=PropertiesUtil.getValue("jdbc.password");
        //连接池相关的属性赋值
        MIN_POOL_NUN=Integer.parseInt(PropertiesUtil.getValue("jdbc.minPoolNum"));
        MAX_POOL_NUN=Integer.parseInt(PropertiesUtil.getValue("jdbc.maxPoolNum"));
    }

    //初始化连接池
    private  static  void initConnection(){
        for (int rows = 0; rows < MIN_POOL_NUN; rows++) {
            try {
                //每次创建新的链接都往集合尾部追加
                 connectionPool.addLast(DriverManager.getConnection(URL,USER_NAME,PASSWORD));
            } catch (SQLException e) {
                System.out.println("初始化获取链接异常");
                e.printStackTrace();
            }
        }
    }

    //获取链接对象
    public static Connection getConnection(){
        //判断连接池有没可用链接，有就取出顶上链接，没有就创建一个返回
        if (connectionPool.size()>0){
            return connectionPool.removeFirst();
        }
        try {

            return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("获取链接异常");
            e.printStackTrace();
        }
        return null;
    }

    //归还链接的方法
    //connection还回来的链接对象
    //true 归还成功
    public static boolean returnConnection(Connection connection){
        //判断当前连接池集合的size大于等于配置号的最大链接数的话，满了，得丢
        if (connectionPool.size()>=MAX_POOL_NUN){
            try {
                connection.close();
                return true;
            } catch (SQLException e) {
                System.out.println("关闭链接异常");
                e.printStackTrace();
            }
        }else {
            connectionPool.addLast(connection);
            return true;
        }
        return false;
    }

    public static boolean closeAll(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet){
        if (resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println("关闭结果集失败");
                e.printStackTrace();
            }
        }

        if (preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println("关闭preparedStatement失败");
                e.printStackTrace();
            }
        }
        return returnConnection(connection);
    }
}
