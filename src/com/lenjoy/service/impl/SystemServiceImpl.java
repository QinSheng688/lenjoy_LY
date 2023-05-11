package com.lenjoy.service.impl;

import com.lenjoy.dao.SysUserInfoDao;
import com.lenjoy.dao.impl.SysUserInfoDaoImpl;
import com.lenjoy.entity.SysUserInfo;
import com.lenjoy.service.MenuInfoService;
import com.lenjoy.service.SystemService;
import com.lenjoy.utils.MD5Utils;
import com.lenjoy.utils.SessionUtil;
import com.lenjoy.utils.TrendsSwitchUtil;

import java.util.Scanner;

public class SystemServiceImpl implements SystemService {
    private SysUserInfoDao sysUserInfoDao=new SysUserInfoDaoImpl();
    private MenuInfoService menuInfoService=new MenuInfoServiceImpl();
    @Override
    public void login() {
        Scanner input=new Scanner(System.in);
        System.out.println("=======乐享洛阳-后台管理系统-登录=========");
        int errNum=0;
        boolean flag=true;
        while (flag){


        System.out.println("输入用户名");
        String userName = input.next();
        System.out.println("输入用户密码");
        String password = input.next();
        //去数据库查询有没有这个用户
            //密码加密（规则：密码先加密一次，把用户名当作盐，再对加密后的密码进行加密）
        SysUserInfo sysUserInfo = sysUserInfoDao.getSysUserIbfoByUserNameAndPassword(userName, MD5Utils.encryptMD5(password,userName));

        if (sysUserInfo!=null){
            flag=false;
            SessionUtil.sysUserInfo=sysUserInfo;
            System.out.println("登录成功，欢迎您"+sysUserInfo.getName());
            //登录成功后展示登录成功后的系统菜单
            menuInfoService.showMenu(SessionUtil.menuInfo.getId());

            //执行选定菜单对应的方法
            TrendsSwitchUtil.invokeMethod();


        }else {
            errNum++;
            System.out.println("错误次数"+errNum+"次，三次退出系统");
            System.out.println("账号或密码错误");
        }if (errNum>=3){
            System.out.println("再见");
            System.exit(0);
        }

    }
    }

    @Override
    public void menuSettings() {
        menuInfoService.showMenu(SessionUtil.menuInfo.getId());
        TrendsSwitchUtil.invokeMethod();
    }
}
