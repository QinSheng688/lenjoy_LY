package com.lenjoy.dao;

import com.lenjoy.entity.MenuInfo;

import java.util.List;

public interface MenuInfoDao {
    //根据菜单父类id和菜单类型获取菜单集合
    //pId父级菜单id
    //type 菜单类型
    //返回菜单集合

    List<MenuInfo> getMenuInfoListByPId(Integer pId);

    //添加菜单
    //menu菜单对象
    //返回受影响行数用来判断是否添加成功
    int addMenuInfo(MenuInfo menuInfo);
}
