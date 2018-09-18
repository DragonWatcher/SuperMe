package com.ahav.system.entity;

/**
 * 部门实体类 <br>
 * 类名：Dept<br>
 * 作者： mht<br>
 * 日期： 2018年8月9日-下午8:54:45<br>
 */
public class Dept {
    private String deptId;
    private String deptName;
    /** 父级部门（或分公司）id */
    private String parentId;
    /** 排序值 */
    private Integer deptRank;
    private String describe;
    
    public Dept(String deptId) {
        this.deptId = deptId;
    }
    
    public Dept(String deptId, String deptName, String parentId, Integer deptRank, String describe) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.parentId = parentId;
        this.deptRank = deptRank;
        this.describe = describe;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getDeptRank() {
        return deptRank;
    }

    public void setDeptRank(Integer deptRank) {
        this.deptRank = deptRank;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deptId == null) ? 0 : deptId.hashCode());
        result = prime * result + ((deptName == null) ? 0 : deptName.hashCode());
        result = prime * result + ((deptRank == null) ? 0 : deptRank.hashCode());
        result = prime * result + ((describe == null) ? 0 : describe.hashCode());
        result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Dept other = (Dept) obj;
        if (deptId == null) {
            if (other.deptId != null)
                return false;
        } else if (!deptId.equals(other.deptId))
            return false;
        if (deptName == null) {
            if (other.deptName != null)
                return false;
        } else if (!deptName.equals(other.deptName))
            return false;
        if (deptRank == null) {
            if (other.deptRank != null)
                return false;
        } else if (!deptRank.equals(other.deptRank))
            return false;
        if (describe == null) {
            if (other.describe != null)
                return false;
        } else if (!describe.equals(other.describe))
            return false;
        if (parentId == null) {
            if (other.parentId != null)
                return false;
        } else if (!parentId.equals(other.parentId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "{deptId : " + deptId + ", deptName : " + deptName + ", parentId : " + parentId + ", deptRank : "
                + deptRank + ", describe : " + describe + "}";
    }

}
