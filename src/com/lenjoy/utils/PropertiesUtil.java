package com.lenjoy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {


    private static Properties properties;
    private PropertiesUtil(){}

    //使用静态代码块创建对象（单例模式）
    static {
        properties=new Properties();
    }


    public  static  void load(String fileName){

        //把配置文件转成一个输入流
        InputStream resourceAsStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName + ".properties");
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            System.out.println("加载配置文件异常");
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        return properties.get(key).toString();
    }


}
