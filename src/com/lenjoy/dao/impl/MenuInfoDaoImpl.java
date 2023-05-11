package com.lenjoy.dao.impl;

import com.lenjoy.dao.MenuInfoDao;
import com.lenjoy.entity.MenuInfo;
import com.lenjoy.utils.BaseDao;

import java.util.Date;
import java.util.List;

public class MenuInfoDaoImpl extends BaseDao<MenuInfo>implements MenuInfoDao {
    @Override
    public List<MenuInfo> getMenuInfoListByPId(Integer pId) {
        String sql="select * from t_menu_info where p_id=?";
        return selectListForObject(sql,MenuInfo.class,pId);
    }

    @Override
    public int addMenuInfo(MenuInfo menuInfo) {
        String sql="insert into t_menu_info values(null,?,?,?,?,?,?,now(),?,now(),?,default)";
        return executeUpdate(sql,
                menuInfo.getName(),
                menuInfo.getUrl(),
                menuInfo.getIcon(),
                menuInfo.getLevel(),
                menuInfo.getPId(),
                menuInfo.getType(),
                menuInfo.getCreateUser(),
                menuInfo.getUpdateUser()
        );
    }

    @Override
    public MenuInfo getMenuInfoById(Integer id) {
        return selectOne("select * from t_menu_info where id=?",MenuInfo.class,id);
    }
}
