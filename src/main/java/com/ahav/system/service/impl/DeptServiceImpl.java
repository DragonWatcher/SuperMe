package com.ahav.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ahav.system.dao.DeptDao;
import com.ahav.system.entity.Dept;
import com.ahav.system.entity.SystemResult;
import com.ahav.system.service.DeptService;

@Service
public class DeptServiceImpl implements DeptService{
    @Autowired
    private DeptDao deptDao;

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

}
