package com.ahav.system.entity;

import com.alibaba.fastjson.JSONObject;
/**
 * 组织架构实体类
 * <br>类名：OrganizationStructure<br>
 * 作者： mht<br>
 * 日期： 2018年9月16日-下午9:03:34<br>
 */
public class DeptStructure {
    /** 部门名称*/
    private String deptName;
    /** 子部门名称列表*/
    private String[] subDeptNames;
    /** 部门成员列表 */
    private JSONObject[] users;
    
    public String getDeptName() {
        return deptName;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public String[] getSubDeptNames() {
        return subDeptNames;
    }
    public void setSubDeptNames(String[] subDeptNames) {
        this.subDeptNames = subDeptNames;
    }
    public JSONObject[] getUsers() {
        return users;
    }
    public void setUsers(JSONObject[] users) {
        this.users = users;
    }
}
