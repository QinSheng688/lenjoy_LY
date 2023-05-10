package com.lenjoy.controller;

import com.lenjoy.service.MenuInfoService;
import com.lenjoy.service.SystemService;
import com.lenjoy.service.impl.MenuInfoServiceImpl;
import com.lenjoy.service.impl.SystemServiceImpl;
import com.lenjoy.utils.SessionUtil;

public class SystemController {
    private MenuInfoService menuInfoService=new MenuInfoServiceImpl();
    private SystemService systemService=new SystemServiceImpl();
    public void showMenu(){
        System.out.println("=====乐享洛阳-后台管理系统=====");
        systemService.login();

    }
    public void menuSettings(){
        System.out.println("\n=====乐享洛阳-后台管理系统("+SessionUtil.sysUserInfo.getName()+")=====");
        systemService.menuSettings();
    }

}
