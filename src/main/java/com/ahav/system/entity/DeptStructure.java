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
    private String text;
    /** 是否是父节点*/
    private boolean isParent;
    /** 子部门或和列表 */
    private List<DeptStructure> nodes;
    
    public DeptStructure() { }
    
    public DeptStructure(Dept d) {
        this.id = d.getDeptId();
        this.text = d.getDeptName();
        this.isParent = true;
    }
    
    public DeptStructure(User user) {
        this.id = String.valueOf(user.getUserId());
        this.text = user.getTrueName();
        this.isParent = false;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return text;
    }

    public void setName(String name) {
        this.text = name;
    }

    public boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public List<DeptStructure> getChildren() {
        return nodes;
    }

    public void setChildren(List<DeptStructure> children) {
        this.nodes = children;
    }
}
