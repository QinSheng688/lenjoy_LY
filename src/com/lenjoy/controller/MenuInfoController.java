package com.lenjoy.controller;

import com.lenjoy.service.MenuInfoService;
import com.lenjoy.service.impl.MenuInfoServiceImpl;

public class MenuInfoController {

    private MenuInfoService menuInfoService=new MenuInfoServiceImpl();
    public void showMainMenu(){

        //展示主菜单
        menuInfoService.showMainMenu();
    }
    public void addMenu(){
        menuInfoService.addMenu();
    }
}
