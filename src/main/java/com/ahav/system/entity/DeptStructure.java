package com.ahav.system.entity;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 组织架构实体类 <br>
 * 类名：OrganizationStructure<br>
 * 作者： mht<br>
 * 日期： 2018年9月16日-下午9:03:34<br>
 */
public class DeptStructure {
    private String deptId;
    /** 部门名称 */
    private String deptName;
    /** 子部门名称列表 */
    private List<DeptStructure> subDeptStructure;
    /** 部门成员列表 */
    private List<JSONObject> users;
    
    public DeptStructure(Dept d) {
        this.deptId = d.getDeptId();
        this.deptName = d.getDeptName();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<DeptStructure> getSubDeptStructure() {
        return subDeptStructure;
    }

    public void setSubDeptStructure(List<DeptStructure> subDeptStructure) {
        this.subDeptStructure = subDeptStructure;
    }

    public List<JSONObject> getUsers() {
        return users;
    }

    public void setUsers(List<JSONObject> users) {
        this.users = users;
    }
}
