package com.ahav.system.enums;

/**
 * 网易邮箱api func值
 * <br>类名：NtesFunc<br>
 * 作者： mht<br>
 * 日期： 2018年8月29日-上午10:58:43<br>
 */
public enum NtesFunc {
    UNIT_GET_UNIT("/unit/getUnit", "获取部门信息"),
    UNIT_GET_ACCOUNT_LIST("/unit/getAccountList", "获取帐号列表"),
    UNIT_GET_UNIT_LIST("/unit/getUnitList", "获取所有部门列表"),
    UNIT_GET_SUB_UNIT_LIST("/unit/getSubUnitList", "获取子部门列表"),
    
    ACCOUNT_GET_ACCOUNT("/account/getAccount", "获取帐号信息"),
    ACCOUNT_GET_ACCOUNT_ALIAS_LIST("/account/getAccountAliasList", "获取帐号别名列表"),
    
    DOMAIN_GET_DOMAIN("/domain/getDomain", "获取企业信息");
    /** 网易邮箱接口func值*/
    private String func;
    /** func值描述*/
    private String desc;
    
    private NtesFunc(String func, String desc) {
        this.func = func;
        this.desc = desc;
    }
    
    public String func() {
        return this.func;
    }
    
    public String desc() {
        return this.desc;
    }

}
