package com.lenjoy.service.impl;

import com.lenjoy.dao.MenuInfoDao;
import com.lenjoy.dao.impl.MenuInfoDaoImpl;
import com.lenjoy.entity.MenuInfo;
import com.lenjoy.service.MenuInfoService;
import com.lenjoy.utils.SessionUtil;
import com.lenjoy.utils.TrendsSwitchUtil;

import java.util.List;
import java.util.Scanner;

public class MenuInfoServiceImpl implements MenuInfoService {
    private MenuInfoDao menuInfoDao=new MenuInfoDaoImpl();
    @Override
    public void showMainMenu() {
        System.out.println("====乐享洛阳====");
        showMenu(-1);
        //把选择到的菜单对象存到session中
        TrendsSwitchUtil.invokeMethod();
    }

    @Override
    public void showMenu(Integer pId) {
        Scanner input=new Scanner(System.in);
        //查询数据库拿到一级菜单集合
        List<MenuInfo> menuInfoList=menuInfoDao.getMenuInfoListByPId(pId);
        //遍历集合，生成菜单
        for (int i = 0; i < menuInfoList.size(); i++) {
            System.out.println("\t"+(i+1)+":"+menuInfoList.get(i).getName());
        }

        boolean flag=false;
        do {
            System.out.println("请选择菜单");
            int menuId=input.nextInt();
            if (menuId>0 &&menuId<=menuInfoList.size()){
                SessionUtil.menuInfo= menuInfoList.get(menuId-1);
            }else {
                System.out.println("没有这个菜单");
                flag=true;
            }
        } while (flag);

    }

    @Override
    public void addMenu() {
        Scanner sc=new Scanner(System.in);
        Integer pId=getMenuInfoPid();
        MenuInfo menuInfo=new MenuInfo();
        System.out.println("菜单名");
        menuInfo.setName(sc.next());
        System.out.println("菜单url");
        menuInfo.setUrl(sc.next());
        System.out.println("菜单小图标");
        menuInfo.setIcon(sc.next());
        System.out.println("菜单序号");
        menuInfo.setLevel(sc.nextInt());
        System.out.println("菜单类型");
        menuInfo.setType(sc.nextInt());
        System.out.println("上级编号");
        menuInfo.setPId(pId);
        menuInfo.setCreateUser(SessionUtil.menuInfo.getId());
        menuInfo.setUpdateUser(SessionUtil.menuInfo.getId());
        int rows=menuInfoDao.addMenuInfo(menuInfo);
        System.out.println(rows>0 ?"添加成功":"添加失败");
    }
    private Integer getMenuInfoPid(){
        return SessionUtil.menuInfo.getId();
    }
}
