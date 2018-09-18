package com.ahav.system.entity;

import java.util.List;

/**
 * 组织架构实体类 <br>
 * 类名：OrganizationStructure<br>
 * 作者： mht<br>
 * 日期： 2018年9月16日-下午9:03:34<br>
 */
public class DeptStructure {
    private String id;
    /** 部门名称 */
    private String name;
    /** 是否是父节点*/
    private boolean isParent;
    /** 子部门或和列表 */
    private List<DeptStructure> children;
    
    public DeptStructure() { }
    
    public DeptStructure(Dept d) {
        this.id = d.getDeptId();
        this.name = d.getDeptName();
        this.isParent = true;
    }
    
    public DeptStructure(User user) {
        this.id = String.valueOf(user.getUserId());
        this.name = user.getTrueName();
        this.isParent = false;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public List<DeptStructure> getChildren() {
        return children;
    }

    public void setChildren(List<DeptStructure> children) {
        this.children = children;
    }
}
