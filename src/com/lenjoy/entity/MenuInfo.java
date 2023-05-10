package com.lenjoy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuInfo  /*extends BaseEntity*/{

    //菜单编号
    private Integer id;
    //名称
    private String name;
    private String url;
    //小图标
    private String icon;
    //等级
    private Integer level;
    //父级id
    private Integer pId;

    //菜单类型 0商家 1用户 2代理商 3管理系统的 4代理商管理系统
    private Integer type;


    //创建时间
    private Date createTime;
    //创建人
    private Integer createUser;
    //修改时间
    private Date updateTime;
    //修改人
    private Integer updateUser;
    //状态
    private Integer status;




}
