package com.ahav.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ahav.system.dao.DeptDao;
import com.ahav.system.entity.Dept;
import com.ahav.system.entity.SystemResult;
import com.ahav.system.enums.NtesDataVer;
import com.ahav.system.enums.NtesFunc;
import com.ahav.system.rsatool.HttpPost;
import com.ahav.system.rsatool.RSASignatureToQiye;
import com.ahav.system.service.NtesService;
import com.ahav.system.util.SystemConstant;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class NtesServiceImpl implements NtesService {
    
    private static final Logger logger = LoggerFactory.getLogger(NtesServiceImpl.class);
    
    @Autowired
    private DeptDao deptDao;
    
    @Override
    public SystemResult updLocalDeptTable() {
        // 1. 获取网易邮箱合作企业部门列表
        // 1.1 查询部门列表版本号
        Long unitVersionDB = deptDao.selectUnitDataVer(NtesDataVer.UNIT_VER);
        JSONObject apiResult = getUnitList(unitVersionDB);
        if (!apiResult.getBooleanValue("suc")) {
            logger.info("网易邮箱接口请求错误码,error_code>>>" + apiResult.getString("error_code"));
            return new SystemResult(HttpStatus.OK.value(), "获取部门列表失败！", apiResult.getString("error_code"));
        }
        // 2. 更新数据库
        String updOk = "部门信息更新成功！";
        // 2.1 版本号一致，不需要更新数据库
        Long verFromNtes = apiResult.getLong("ver");
        if (verFromNtes == unitVersionDB) {
            return new SystemResult(HttpStatus.OK.value(), updOk, Boolean.TRUE);
        }

        // 2.2 更新dept表中的数据
        JSONArray unitListArr = apiResult.getJSONArray("con");
        List<Dept> deptList = new ArrayList<>();
        // unitListArr 转化为 List<Dept> deptList
        unitListArr.forEach((o) -> {
            JSONObject unit = JSONObject.parseObject(JSONObject.toJSONString(o));
            deptList.add(new Dept(unit.getString("unit_id"), unit.getString("unit_name"), unit.getString("parent_id"),
                    unit.getInteger("unit_rank"), ""));
        });
        // 2.2.1 查询返回列表中的数据在数据库中的状态 并执行insert 或 update
        deptList.forEach((unit) -> {
            Dept deptDB = deptDao.selectDeptById(unit.getDeptId());
            if (deptDB == null) {// 添加新部门
                deptDao.insertDept(unit);
            } else {
                if (!unit.equals(deptDB))
                    deptDao.updateDept(unit);
            }
        });
        // 2.2.2 提取网易部门列表的部门id
        List<String> deptIdListNtes = new ArrayList<>();
        deptList.forEach((d) -> deptIdListNtes.add(d.getDeptId()));

        List<String> deptIdListDB = deptDao.selectDeptIdList();
        deptIdListNtes.forEach((unitId) -> {
            // 相同则移除数据库中的部门id列表，剩下的就是数据库中多余的部门id列表
            if (deptIdListDB.contains(unitId))
                deptIdListDB.remove(unitId);
        });
        // 执行（多余的）部门批量删除
        deptDao.delDeptsBatch(deptIdListDB);
        
        // 更新版本号
        new Thread(() -> deptDao.updateDataVer(NtesDataVer.UNIT_VER, verFromNtes), "saveDataVerThread").start();

        return new SystemResult(HttpStatus.OK.value(), updOk, Boolean.TRUE);
    }
    
    /**
     * 获取网易邮箱合作企业部门列表
     * <br>作者： mht<br> 
     * 时间：2018年8月29日-下午2:55:53<br>
     * @param unitVersion
     * @return
     */
    private JSONObject getUnitList(Long unitVersion) {
        String url = SystemConstant.NTES_API_BASE_URL + NtesFunc.UNIT_GET_UNIT_LIST.func();
        long time = System.currentTimeMillis();

        String sign = "domain=" + SystemConstant.AHAV_DOMAIN + "&product=" + SystemConstant.QIYE_PRODUCT + "&time="
                + time + (unitVersion != null ? ("&ver=" + unitVersion) : "");
        // 签名
        sign = RSASignatureToQiye.generateSigature(SystemConstant.PRI_KEY, sign);
        // url生成
        url = url + "?" + "domain=" + SystemConstant.AHAV_DOMAIN + "&product=" + SystemConstant.QIYE_PRODUCT + "&sign="
                + sign + "&time=" + time + (unitVersion != null ? ("&ver=" + unitVersion) : "");

        String res = new HttpPost().post(url);
        JSONObject apiResult = JSONObject.parseObject(res);

        return apiResult;
    }

}
