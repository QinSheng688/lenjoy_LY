package com.lenjoy.service.impl;

import com.lenjoy.dao.BusinessInfoDao;
import com.lenjoy.dao.impl.BusinessInfoDaoImpl;
import com.lenjoy.entity.BusinessInfo;
import com.lenjoy.service.BusinessInfoService;
import com.lenjoy.service.MenuInfoService;
import com.lenjoy.utils.MD5Utils;
import com.lenjoy.utils.SessionUtil;
import com.lenjoy.utils.TrendsSwitchUtil;

import java.util.Scanner;

public class BusinessInfoServiceImpl implements BusinessInfoService {
    @Override
    public void showMenu() {
        MenuInfoService menuInfoService=new MenuInfoServiceImpl();
        menuInfoService.showMenu(SessionUtil.menuInfo.getId());
        TrendsSwitchUtil.invokeMethod();
    }

    private BusinessInfoDao businessInfoDao=new BusinessInfoDaoImpl();
    @Override
    public void settleIn() {
        Scanner sc=new Scanner(System.in);
        System.out.println("\n=========商家入驻==========");
        System.out.println("请输入店铺名");
        String name=sc.next();
        System.out.println("请输入账号");
        String userName=sc.next();
        System.out.println("请输入密码");
        String password=sc.next();
        System.out.println("请输入联系人姓名");
        String lxrName=sc.next();
        System.out.println("请输入联系方式");
        String tel=sc.next();
        System.out.println("请输入店铺位置");
        String address=sc.next();
        //构建BusinessInfo对象
        BusinessInfo businessInfo=new BusinessInfo();

        businessInfo.setName(name);
        businessInfo.setUserName(userName);
        //加密
        businessInfo.setPassword(MD5Utils.encryptMD5(password,userName));
        businessInfo.setLxrName(lxrName);
        businessInfo.setTel(tel);
        businessInfo.setAddress(address);
        int rows= businessInfoDao.addBusinessInfo(businessInfo);
        System.out.println(rows>0?"入驻成功":"入驻失败");
        TrendsSwitchUtil.roolbackMethod();


    }


}
