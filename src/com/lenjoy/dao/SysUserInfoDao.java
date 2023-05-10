package com.lenjoy.dao;

import com.lenjoy.entity.SysUserInfo;

public interface SysUserInfoDao {
    //根据账号密码获取系统用户对象并返回
    SysUserInfo getSysUserIbfoByUserNameAndPassword(String userName, String password);
}
