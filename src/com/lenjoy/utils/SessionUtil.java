package com.lenjoy.utils;

import com.lenjoy.entity.MenuInfo;
import com.lenjoy.entity.SysUserInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//模拟session
public abstract class SessionUtil {
    public static MenuInfo menuInfo;
    public static SysUserInfo sysUserInfo;
    public static Map<String, Object> sessionMap=new HashMap<>();
    public static void setAttribute(String key,Object value) {
        sessionMap.put(key,value);

    }


    public static Object getAttribute(String key){
        return sessionMap.get(key);
    }
}
