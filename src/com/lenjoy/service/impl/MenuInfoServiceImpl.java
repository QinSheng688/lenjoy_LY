package com.lenjoy.service.impl;

import com.lenjoy.dao.MenuInfoDao;
import com.lenjoy.dao.impl.MenuInfoDaoImpl;
import com.lenjoy.entity.MenuInfo;
import com.lenjoy.service.MenuInfoService;
import com.lenjoy.service.SystemService;
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
        System.out.println(pId==-1?"\t[0|其它]：退出系统":"\t[0|其它]：返回上一级");

        boolean flag=false;
        do {
            System.out.println("请选择菜单");
            String menuIdStr=input.next();
            int menuId=0;
            //判断输入的编号是否为0，是就判断
                if ("0".equals(menuIdStr)){
                    //是就判断pid是否为-1
                    if (pId==-1){
                        System.exit(0);
                    }else {
                        //如果不等执行返回上一级菜单的方法
                        TrendsSwitchUtil.roolbackMethod();
                    }

                }
                try {
                    //字符串转数字
                    menuId = Integer.parseInt(menuIdStr);
                } catch (NumberFormatException e) {
                    //转换出错时，（用户选择了其它的处理逻辑）
                    if (pId==-1){
                        System.exit(0);
                    }else {
                        TrendsSwitchUtil.roolbackMethod();
                    }
                }

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
        Scanner sc = new Scanner(System.in);
        Integer pId = getMenuInfoPid(-1);
        //如果pid为空，说明用户取消了添加菜单的操作
        if (pId != null) {
            //调用添加菜单的上级目录的方法
            SystemService systemService = new SystemServiceImpl();
            systemService.menuSettings();
        }
        MenuInfo menuInfo = new MenuInfo();
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
        menuInfo.setPId(pId);
        menuInfo.setCreateUser(SessionUtil.menuInfo.getId());
        menuInfo.setUpdateUser(SessionUtil.menuInfo.getId());
        int rows = menuInfoDao.addMenuInfo(menuInfo);
        System.out.println(rows > 0 ? "添加成功" : "添加失败");
        TrendsSwitchUtil.roolbackMethod();
    }
    private Integer getMenuInfoPid(Integer pId) {

        System.out.println("\n-----菜单管理-添加菜单-菜单列表------");
        List<MenuInfo> menuInfoList= menuInfoDao.getMenuInfoListByPId(pId);
        for (int i = 0; i < menuInfoList.size(); i++) {
            System.out.println("\t"+(i+1)+menuInfoList.get(i).getName());
        }
        System.out.println("请选择");
        System.out.println("[1]:添加菜单\t[2]:选择菜单添加子菜单[其它]取消");
        Scanner input=new Scanner(System.in);
        System.out.println("请选择编号：");
        String num=input.next();
        switch (num){
            case "1":
                return pId;

            case "2":
                System.out.println("请选择父级菜单编号：");
                int menuId=input.nextInt();
                return getMenuInfoPid(menuInfoList.get(Integer.parseInt(num)-1).getId());

            default:
                return null;

        }

    }
}
