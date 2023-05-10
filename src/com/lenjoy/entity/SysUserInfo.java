package com.lenjoy.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.util.Date;


/**
* 
* @TableName t_sysuser_info
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserInfo {

    private Integer id;
    /**
     *
     */
    private String userName;
    /**
     *
     */

    private String password;
    /**
     * 员工姓名
     */

    private String name;
    /**
     *
     */

    private String tel;
    /**
     *
     */

    private Date createTime;
    /**
     *
     */

    private Integer createUser;
    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    private Integer updateUser;
    /**
     *
     */
    private Integer status;

}
