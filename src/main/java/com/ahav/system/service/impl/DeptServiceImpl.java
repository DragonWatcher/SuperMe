package com.ahav.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ahav.system.dao.DeptDao;
import com.ahav.system.dao.UserDao;
import com.ahav.system.entity.Dept;
import com.ahav.system.entity.DeptStructure;
import com.ahav.system.entity.SystemResult;
import com.ahav.system.entity.User;
import com.ahav.system.service.DeptService;
import com.alibaba.fastjson.JSONObject;

@Service
public class DeptServiceImpl implements DeptService{
    @Autowired
    private DeptDao deptDao;
    @Autowired
    private UserDao userDao;

    @Override
    public SystemResult allDepts() {
        List<Dept> depts = deptDao.allDepts();
        SystemResult res = new SystemResult(HttpStatus.OK.value(), null, depts);
        
        return res;
    }

    public static class DeptSettings {
        /** 保留的部门信息列表*/
        private List<Dept> deptList;
        /** 删除的部门列表*/
        private List<Dept> delDeptList;

        public List<Dept> getDeptList() {
            return deptList;
        }
        public void setDeptList(List<Dept> deptList) {
            this.deptList = deptList;
        }
        public List<Dept> getDelDeptList() {
            return delDeptList;
        }
        public void setDelDeptList(List<Dept> delDeptList) {
            this.delDeptList = delDeptList;
        }
    }
    
    @Override
    public SystemResult viewDeptsAndUsers() {
        List<DeptStructure> deptStructure = new ArrayList<>();
        // 查询父级部门，即parentId为null的部门
        deptDao.selectParentDepts().forEach(parent -> deptStructure.add(new DeptStructure(parent)));

        deptStructure.forEach(ds -> packagingDepts(ds));

        return new SystemResult(HttpStatus.OK.value(), "组织架构查看", deptStructure);
    }
    
    @Override
    public SystemResult saveDeptSettings(DeptSettings depts) {
        List<Dept> deptList = depts.getDeptList();
        List<Dept> delDeptList = depts.getDelDeptList();
        // 执行删除
        for (Dept dept : delDeptList) {
            deptDao.delDeptById(dept.getDeptId());
        }
        // 添加或更新
        for (Dept dept : deptList) {
            if (dept.getDeptId() != null) {// 更新部门信息
                deptDao.updateDept(dept);
            } else {// 添加部门信息
                deptDao.insertDept(dept);
            }
        }

        return new SystemResult(HttpStatus.OK.value(), "保存部门设置成功！", deptDao.allDepts());
    }

    @Override
    public Dept getDeptById(String deptId) {
        return deptDao.getDeptById(deptId);
    }
    
    /**
     * 递归实现-组织架构封装
     * <br>作者： mht<br> 
     * 时间：2018年9月16日-下午10:59:44<br>
     * @param deptStructure
     */
    private void packagingDepts(DeptStructure deptStructure) {
        // 1. 查询部门成员列表
        List<JSONObject> deptUsersJoList = new ArrayList<>();
        userDao.selectUsersByDept(deptStructure.getDeptId()).forEach(user -> {
            // 为了前端观察方便，对其他字段进行隐藏，采用jsonobject对象返回
            JSONObject userJo = new JSONObject();
            userJo.put("userId", user.getUserId());
            userJo.put("username", user.getUsername());
            userJo.put("trueName", user.getTrueName());
            userJo.put("email", user.getEmail());

            deptUsersJoList.add(userJo);
        });
        deptStructure.setUsers(deptUsersJoList.size() == 0 ? null : deptUsersJoList);
        List<DeptStructure> subDeptStructs = new ArrayList<>();
        // 2. 查询子部门列表，并将Dept转换为DeptStructure，存入subDeptStructs
        deptDao.selectSubDepts(deptStructure.getDeptId()).forEach(d -> subDeptStructs.add(new DeptStructure(d)));
        deptStructure.setSubDeptStructure(subDeptStructs.size() == 0 ? null : subDeptStructs);
        // 3. 递归
        subDeptStructs.forEach(subDS -> packagingDepts(subDS));
    }

}
