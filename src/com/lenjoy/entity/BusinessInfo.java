package com.lenjoy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessInfo {
    private Integer id;
    private String name;
    private String userName;
    private String password;
    private String lxrName;
    private String tel;
    private String address;
    private Integer jobType;
    private Date createTime;
    private Integer status;
}
