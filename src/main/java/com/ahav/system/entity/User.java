package com.ahav.system.entity;

/**
 * sys_user表对应实体类
 * <br>类名：User<br>
 * 作者： mht<br>
 * 日期： 2018年8月3日-上午10:24:07<br>
 */
import java.util.Date;

/**
 * 系统用户 <br>
 * 类名：User<br>
 * 作者： mht<br>
 * 日期： 2018年8月3日-上午10:55:00<br>
 */
public class User extends SimpleUser {
    private String pwd;
    /** 用户真实姓名 */
    private String email;
    /** 规则: @see: com.ahav.util.Encrypt.genSalt */
    private String salt;
    private Date createTime;
    private String creator;
    private Date editTime;
    private String editor;
    private String description;

    public User() {
    }

    /**
     * User u = (User) simpleUser; TODO:强转报错！！时间宽裕时进一步测试。
     * 
     * @param simpleUser
     */
    public User(SimpleUser simpleUser) {
        this.userId = simpleUser.userId;
        this.username = simpleUser.username;
        this.trueName = simpleUser.trueName;
        this.dept = simpleUser.dept;
        this.role = simpleUser.role;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
