package com.ahav.system.entity;
/**
 * 部门实体类
 * <br>类名：Dept<br>
 * 作者： mht<br>
 * 日期： 2018年8月9日-下午8:54:45<br>
 */
public class Dept {
    private Integer deptId;
    private String deptName;
    private String describe;
    
    public Integer getDeptId() {
        return deptId;
    }
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
    public String getDeptName() {
        return deptName;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public String getDescribe() {
        return describe;
    }
    public void setDescribe(String describe) {
        this.describe = describe;
    }
    @Override
    public String toString() {
        return "{deptId : " + deptId + ", deptName : " + deptName + ", describe : " + describe + "}";
    }
    
}
