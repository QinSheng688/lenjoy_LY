package com.lenjoy.dao.impl;

import com.lenjoy.dao.BusinessInfoDao;
import com.lenjoy.entity.BusinessInfo;
import com.lenjoy.service.MenuInfoService;
import com.lenjoy.service.SystemService;
import com.lenjoy.service.impl.MenuInfoServiceImpl;
import com.lenjoy.utils.BaseDao;
import com.lenjoy.utils.SessionUtil;

public class BusinessInfoDaoImpl extends BaseDao<BusinessInfo> implements BusinessInfoDao {
    @Override
    public int addBusinessInfo(BusinessInfo businessInfo) {
        String sql="insert into business_info values(null,?,?,?,?,?,?,default,now(),default,default)";
        return executeUpdate(sql,
                businessInfo.getName(),
                businessInfo.getUserName(),
                businessInfo.getPassword(),
                businessInfo.getLxrName(),
                businessInfo.getTel(),
                businessInfo.getAddress()
                );
    }


}
