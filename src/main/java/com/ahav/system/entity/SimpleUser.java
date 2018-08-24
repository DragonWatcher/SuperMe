package com.ahav.system.entity;

/**
 * 简单用户类型，
 * 用于页面信息获取敏感信息
 * 
 * <br>类名：SimpleUser<br>
 * 作者： mht<br>
 * 日期： 2018年8月5日-下午7:43:26<br>
 */
public class SimpleUser {
    /** 用户id，为数据库自增*/
    protected Integer userId;
    /** 用户账号*/
    protected String  username;
    /** 用户真实姓名*/
    protected String trueName;
    /** 账号对应的界面颜色*/
    private String color;
    /** 用户所在部门*/
    protected Dept dept;
    /** 用户角色*/
    protected Role role;
    
    public SimpleUser() {
    }
    /**
     * 敏感用户转简单用户
     * @param user
     */
    public SimpleUser(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.dept = user.getDept();
        this.trueName = user.getTrueName();
        this.role = user.role;
        this.color = user.getColor();
    }
    
    @Override
    public String toString() {
        return "{userId : " + userId + ", username : " + username + ", trueName : " + trueName + ", dept : " + dept
                + ", role : " + role + "}";
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Dept getDept() {
        return dept;
    }
    public void setDept(Dept dept) {
        this.dept = dept;
    }
    public String getTrueName() {
        return trueName;
    }
    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
}
