package com.ahav.system.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.tomcat.jni.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ahav.system.dao.UserDao;
import com.ahav.system.dao.UserRoleDao;
import com.ahav.system.entity.SimpleUser;
import com.ahav.system.entity.SystemResult;
import com.ahav.system.entity.User;
import com.ahav.system.enums.NtesFunc;
import com.ahav.system.rsatool.HttpPost;
import com.ahav.system.rsatool.RSASignatureToQiye;
import com.ahav.system.service.LoginService;
import com.ahav.system.service.NtesService;
import com.ahav.system.service.UserService;
import com.ahav.system.util.Encrypt;
import com.ahav.system.util.SystemConstant;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * UserService实现类 <br>
 * 类名：UserServiceImpl<br>
 * 作者： mht<br>
 * 日期： 2018年8月5日-下午7:10:12<br>
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private LoginService loginService;

    /**
     * @author wxh
     */
    @Override
    public User findByName(String username) {
        User user = userDao.findByName(username);
        return user;
    }

    @Override
    public SystemResult getUserById(Integer userId) {
        SimpleUser userDB = userDao.selectUserById(userId);
        SystemResult res = new SystemResult(HttpStatus.OK.value(), null, userDB);
        return res;
    }

    @Override
    public SystemResult getUserByName(String username) {
        User userDB = userDao.selectUserByName(username);
        SystemResult res = new SystemResult(HttpStatus.OK.value(), null, userDB);
        return res;
    }

    @Override
    public SystemResult checkUsername(String username) {
        boolean unused = true;
        // 查询指定用户账号
        String usernameDB = userDao.selectUsername(username);
        if (usernameDB != null) {
            unused = false;
            return new SystemResult(HttpStatus.OK.value(), "用户名重复！", unused);
        }

        return new SystemResult(HttpStatus.OK.value(), "用户名可用！", unused);
    }

    @Override
    public SystemResult createOrUpdUser(User user) {
        SystemResult res = new SystemResult();
        if (user.getUserId() == null) {
            res = insertNewUser(user);
        } else {
            res = updUser(user);
        }
        return res;
    }

    /**
     * 原型中只有：角色(roleId)、部门(deptId)、姓名(trueName)、账号(username)<br>
     * 这四项，因此存库的时候需要进行细化用户处理
     */
    private SystemResult insertNewUser(User newUser) {
        // 细化用户信息，包括初始密码、加密盐值、创建时间、创建人
        // 散列原始密码
        String[] hashedCredentials = Encrypt.encryptPassword(newUser.getUsername(), newUser.getTrueName(),
                SystemConstant.ORIGINAL_PASSWORD);
        // 封装密码与盐值
        newUser.setPwd(hashedCredentials[0]);
        newUser.setSalt(hashedCredentials[1]);
        // 封装其他信息
        newUser.setCreateTime(new Date());

        // 从当前用户的认证信息中获取身份认证信息，登录时添加的身份认证信息为一个从数据库中查出的user对象。
        User currUser = (User) SecurityUtils.getSubject().getPrincipal();
        newUser.setCreator(currUser.getUsername());

        // 持久化新成员
        boolean executeResult = true;
        try {
            executeResult = userDao.insertUser(newUser);
            // 添加成员-角色之间对应关系
            userRoleDao.insertUserRole(newUser);

            logger.info("创建新用户：{}", newUser.toString());
            // 以User作为数据库存储对象，以SimpleUser作为返回值对象，将User对象转型为父类，为了防止页面获得敏感信息
            SystemResult result = new SystemResult(HttpStatus.CREATED.value(), "添加新成员成功！", new SimpleUser(newUser));
            if (!executeResult) {
                // 执行结果为false则添加成员失败！
                result = new SystemResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "添加新成员失败！", null);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            // 出现异常则添加成员失败！
            return new SystemResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "添加新成员失败！", null);
        }
    }

    /**
     * 更新用户 <br>
     * 将会更新的信息有：角色(role关联关系)、部门(department_id)、姓名(true_name)、账号(username)
     * 以及：更新时间(edit_time)和更新人(editor) 作者： mht<br>
     * 时间：2018年8月11日-下午4:48:14<br>
     */
    private SystemResult updUser(User user) {
        // 修改时间
        user.setEditTime(new Date());
        // 从当前用户的认证信息中获取身份认证信息，登录时添加的身份认证信息为一个从数据库中查出的user对象。
        User currUser = (User) SecurityUtils.getSubject().getPrincipal();
        user.setEditor(currUser.getUsername());
        // 持久化新成员
        boolean executeResult = true;
        try {
            // 更新用户表
            executeResult = userDao.updateUser(user);
            // 更新用户-角色对应关系
            userRoleDao.updateUserRole(user);

            logger.info("更新用户：{}", user.toString());
            // 以User作为数据库存储对象，以SimpleUser作为返回值对象，将User对象转型为父类，为了防止页面获得敏感信息
            SystemResult result = new SystemResult(HttpStatus.OK.value(), "更新用户成功！", new SimpleUser(user));
            if (!executeResult) {
                // 执行结果为false则添加成员失败！
                result = new SystemResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "更新用户失败！", null);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            // 出现异常失败！
            return new SystemResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "更新用户失败！", null);
        }
    }

    @Override
    public List<User> findUsersByRoleId(Integer roleId) {
        return userDao.selectUsersByRoleId(roleId);
    }

    @Override
    public SystemResult selectUsers(Integer pageNum, Integer pageSize, Integer roleId, Integer deptId,
            String username) {
        // TODO:这个pageHelp有bug
        PageHelper.startPage(pageNum, pageSize);
        List<SimpleUser> users = userDao.selectUsers(roleId, deptId, username);
        PageInfo<SimpleUser> pageInfo = new PageInfo<>(users);
        SystemResult res = new SystemResult(HttpStatus.OK.value(), null, pageInfo);

        return res;
    }

    @Override
    public SystemResult deleteUser(Integer userId) {
        try {
            // 删除用户表对应id用户
            boolean executeResult = userDao.deleteUser(userId);
            // 删除用户-角色表中对应用户id的关联关系
            userRoleDao.deleteUserRoleByUserId(userId);
            if (!executeResult) {
                // 如果executeResult为false 则删除失败
                return new SystemResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "用户删除失败！", null);
            }
            return new SystemResult(HttpStatus.OK.value(), "用户删除成功！", userId);
        } catch (Exception e) {
            e.printStackTrace();
            // 如果出现异常，则删除失败
            return new SystemResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "用户删除失败！", null);
        }
    }

    @Override
    public SystemResult resetPassword(Integer userId) {
        SimpleUser userDB = userDao.selectUserById(userId);
        User user = new User(userDB);
        // 散列原始密码
        String[] hashedCredentials = Encrypt.encryptPassword(user.getUsername(), user.getTrueName(),
                SystemConstant.ORIGINAL_PASSWORD);
        // 封装密码与盐值
        user.setPwd(hashedCredentials[0]);
        user.setSalt(hashedCredentials[1]);

        try {
            userDao.updatePassword(user);
            logger.info("需要重置密码成功！用户名:{}", user.getUsername());
            return new SystemResult(HttpStatus.OK.value(), "重置密码成功!", user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new SystemResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "重置密码失败!", null);
        }
    }

    @Override
    public SystemResult updatePassword(User user) {
        String[] hashedCredentials = Encrypt.encryptPassword(user.getUsername(), user.getTrueName(), user.getPwd());
        user.setPwd(hashedCredentials[0]);
        user.setSalt(hashedCredentials[1]);
        boolean executeResult = userDao.updatePassword(user);
        if (executeResult) {
            loginService.logout();
            return new SystemResult(HttpStatus.OK.value(), "密码修改成功，请重新登录！", executeResult);
        }
        return new SystemResult(HttpStatus.OK.value(), "密码修改失败！", executeResult);
    }

    @Override
    public SystemResult checkPassword(String password) {
        // 获得用户的身份信息，包括salt等
        User currUser = (User) SecurityUtils.getSubject().getPrincipal();
        // 待校验密码加密
        String hashedPwd = Encrypt.encryptPassword(password, currUser.getSalt());

        if (hashedPwd.equals(currUser.getPwd())) {
            return new SystemResult(HttpStatus.OK.value(), "原密码正确", true);
        } else {
            return new SystemResult(HttpStatus.OK.value(), "原密码不正确!", false);
        }
    }

    @Override
    public SystemResult getCurrentUser() {
        User currUser = (User) SecurityUtils.getSubject().getPrincipal();
        SimpleUser simpleUser = new SimpleUser(currUser);
        // color有可能更新
        simpleUser.setColor(userDao.selectUserById(simpleUser.getUserId()).getColor());

        return new SystemResult(HttpStatus.OK.value(), "当前用户", simpleUser);
    }

    @Override
    public SystemResult getUserByTrueName(String trueName) {
        List<SimpleUser> users = userDao.selectUserByTrueName(trueName);
        SystemResult result = new SystemResult(HttpStatus.OK.value(), "用户列表", users);
        return result;
    }

    @Override
    public List<User> selectUserByDeptIdAndRoleId(String deptId, Integer roleId) {
        List<User> users = userDao.selectUserByDeptIdAndRoleId(deptId, roleId);
        return users;
    }

    @Override
    public SystemResult updUserColor(String color) {
        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        SimpleUser user = new SimpleUser();
        user.setUserId(currentUser.getUserId());
        user.setColor(color);

        boolean updUserColor = userDao.updateUserColor(user);
        if (!updUserColor) {
            return new SystemResult(HttpStatus.OK.value(), "界面颜色设置失败", Boolean.FALSE);
        }
        return new SystemResult(HttpStatus.OK.value(), "界面颜色设置成功", color);
    }

    @Override
    public SystemResult updUserProfile(MultipartFile newProfile) {
        String root = System.getProperty("user.dir").replace("\\", "/");
        String profilesPath = root + SystemConstant.STATIC_RES_PATH + SystemConstant.PROFILES_PATH;

        if (!newProfile.isEmpty()) {
            // 当前用户
            User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
            String profilePathAndNameDB = userDao.selectUserById(currentUser.getUserId()).getProfilePath();
            // 默认以原来的头像名称为新头像的名称，这样可以直接替换掉文件夹中对应的旧头像
            String newProfileName = profilePathAndNameDB;
            // 若头像名称不存在
            if (profilePathAndNameDB == null || "".equals(profilePathAndNameDB)) {
                // 可直接访问的图片路径
                newProfileName = SystemConstant.PROFILES_PATH + System.currentTimeMillis() + newProfile.getOriginalFilename();
                // 路径存库
                currentUser.setProfilePath(newProfileName);
                userDao.updateUserProfilePath(currentUser);
            }
            // 磁盘保存
            BufferedOutputStream out = null;
            try {
                File folder = new File(profilesPath);
                if (!folder.exists())
                    folder.mkdirs();
                out = new BufferedOutputStream(new FileOutputStream(root + SystemConstant.STATIC_RES_PATH + newProfileName));
                // 写入新文件
                out.write(newProfile.getBytes());
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
                return new SystemResult(HttpStatus.OK.value(), "设置头像失败", Boolean.FALSE);
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return new SystemResult(HttpStatus.OK.value(), "设置头像成功", newProfileName);
        } else {
            return new SystemResult(HttpStatus.OK.value(), "设置头像失败", Boolean.FALSE);
        }
    }
}
