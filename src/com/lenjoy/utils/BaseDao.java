package com.lenjoy.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseDao<T> {
    private Connection connection=null;
    private PreparedStatement preparedStatement =null;
    private ResultSet resultSet=null;

    /**
     * 通用的 增删改方法
     *
     * @param sql  需要执行的sql其中包含（?占位符）
     * @param objs ?号对应的参数  可变参数列表
     * @return 受影响行数  private Connection connection=null;
     *     private PreparedSta
     */
    public int executeUpdate(String sql,Object... objs){
        int rows=0;
        try {
            //获取链接，从连接处中获取链接
            connection=ConnectionPool.getConnection();
            //获取sql预执行器
            preparedStatement=connection.prepareStatement(sql);
            //替换？占位符
            for (int i = 0; i < objs.length; i++) {
                preparedStatement.setObject(i+1,objs[i]);
            }
            rows=preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("sql执行异常");
            e.printStackTrace();
        }finally {
            ConnectionPool.closeAll(connection,preparedStatement,null);
        }
        return rows;
    }

    //查询数据封装成一个对象
    public T selectOne(String sql,Class clazz,Object... objs){
        Object obj=null;
        try {
            connection=ConnectionPool.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            for (int i = 0; i < objs.length; i++) {
                preparedStatement.setObject(i+1,objs[i]);
            }
            resultSet=preparedStatement.executeQuery();
            if (resultSet.next()){
                obj=clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    String fieldName = fields[i].getName();
                    String methodName="set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                    Method method = clazz.getMethod(methodName, fields[i].getType());
                    method.invoke(obj,resultSet.getObject(i+1));

                }
            }
        } catch (SQLException e) {
            System.out.println("获取链接失败");
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("实例化对象失败");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            System.out.println("未找到set方法");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("赋值属性失败");
            e.printStackTrace();
        } finally {
            ConnectionPool.closeAll(connection,preparedStatement,null);
        }
        return (T)obj;
    }

    public List<T> selectListForObject(String sql, Class clazz, Object... objects) {
        //存放结果的集合
        List<T> list = null;
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            //替换占位符
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i + 1, objects[i]);
            }
            //执行SQL获取结果集
            resultSet = preparedStatement.executeQuery();
            list = new ArrayList<>();
            //遍历结果集，封装对象并且放到list中去
            while (resultSet.next()) {
                //通过反射创建对象
                Object obj = clazz.newInstance();
                //获取所以的字段
                Field[] fields = clazz.getDeclaredFields();
                //根据字段名，获取对应的set方法名
                for (int i = 0; i < fields.length; i++) {
                    //获取字段名
                    String name = fields[i].getName();
                    //根据字段名 -->字段名首字母转大写，在前边拼接上set
                    String firstChar = name.substring(0, 1).toUpperCase();
                    //方法名
                    String methodName = "set" + firstChar + name.substring(1);
                    //根据拼接好的方法名获取方法对象
                    Method method = clazz.getMethod(methodName, fields[i].getType());
                    //指定对应的set方法，完成赋值
                    method.invoke(obj, resultSet.getObject(i + 1));
                }
                list.add((T) obj);
            }
        } catch (SQLException e) {
            System.out.println("数据库连接异常");
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.out.println("没有对应的set方法");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("赋值失败");
            e.printStackTrace();
        } finally {
            ConnectionPool.closeAll(connection,preparedStatement,resultSet);
        }
        return list;
    }

    /**
     * 查询单条数据，把结果封装到map集合中
     *
     * @param sql     执行的sql
     * @param objects 参数列表
     * @return 结果集
     */
    public Map<String, Object> selectOneForMap(String sql, Object... objects) {
        Map<String, Object> map = null;
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            //替换占位符
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i + 1, objects[i]);
            }
            //执行sql获取结果集
            resultSet = preparedStatement.executeQuery();
            map = new HashMap<>();
            //字段名字都在 metaData对象身上
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取结果的总列数
            int columnCount = metaData.getColumnCount();
            if (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    //根据列名从结果集中获取数据
                    Object value = resultSet.getObject(columnName);
                    map.put(columnName, value);
                }
            }
        } catch (SQLException e) {
            System.out.println("数据库连接异常");
            e.printStackTrace();
        } finally {
            ConnectionPool.closeAll(connection,preparedStatement,resultSet);
        }
        return map;
    }

    /**
     * 查询多条数据，把结果封装到list集合中
     *
     * @param sql     执行的SQL
     * @param objects 参数列表
     * @return 结果集
     */
    public List<Map<String, Object>> selectListForMap(String sql, Object... objects) {
        //装结果集的容器
        List<Map<String, Object>> list = null;
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            //替换占位符
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i + 1, objects[i]);
            }
            //执行sql，获取结果集
            resultSet = preparedStatement.executeQuery();
            list = new ArrayList<>();
            //获取metadata对象，方便获取列名
            ResultSetMetaData metaData = resultSet.getMetaData();
            //结果集一共多少列
            int columnCount = metaData.getColumnCount();
            //遍历结果集，封装成map并放入list集合中
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    //获取列名
                    String columnName = metaData.getColumnLabel(i);
                    //根据列名获取结果
                    Object value = resultSet.getObject(columnName);
                    //把columnName作为key 放入map中
                    map.put(columnName, value);
                }
                list.add(map);
            }
        } catch (SQLException e) {
            System.out.println("数据库连接异常");
            e.printStackTrace();
        } finally {
            ConnectionPool.closeAll(connection,preparedStatement,resultSet);
        }
        return list;
    }




}
