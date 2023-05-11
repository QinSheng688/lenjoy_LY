package com.lenjoy.controller;

import com.lenjoy.service.BusinessInfoService;
import com.lenjoy.service.impl.BusinessInfoServiceImpl;

public class BusinessInfoController {
    private BusinessInfoService businessInfoService=new BusinessInfoServiceImpl();
    public void showMenu(){
        businessInfoService.showMenu();
    }
    //商家入驻
    public void settleIn(){
        businessInfoService.settleIn();
    }
}
