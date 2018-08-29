package com.ahav.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.ahav.system.dao.DeptDao;
import com.ahav.system.entity.SystemResult;
import com.ahav.system.enums.NtesFunc;
import com.ahav.system.rsatool.HttpPost;
import com.ahav.system.rsatool.RSASignatureToQiye;
import com.ahav.system.service.NtesService;
import com.ahav.system.util.SystemConstant;
import com.alibaba.fastjson.JSONObject;

public class NtesServiceImpl implements NtesService {
    
    private static final Logger logger = LoggerFactory.getLogger(NtesServiceImpl.class);
    
    @Autowired
    private DeptDao deptDao;
    
    @Override
    public SystemResult updLocalDeptTable() {
        // 1. 获取网易邮箱合作企业部门列表
        // 1.1 查询部门列表版本号
        long unitVersion = deptDao.selectNtesDataVer("unit_ver");
        JSONObject apiResult = getUnitList(unitVersion);
        if (apiResult == null) {
            logger.info("获取部门列表失败！错误码：" + apiResult.getString("error_code"));
            return null;
        }
        // 2. 更新数据库
        
        
        return new SystemResult(HttpStatus.OK.value(), "接口调用成功", apiResult);
    }
    
    /**
     * 获取网易邮箱合作企业部门列表
     * <br>作者： mht<br> 
     * 时间：2018年8月29日-下午2:55:53<br>
     * @return
     */
    private JSONObject getUnitList(long unitVersion) {
        String url = SystemConstant.NTES_API_BASE_URL + NtesFunc.UNIT_GET_UNIT_LIST.func();
        long time = System.currentTimeMillis();

        String sign = "domain=" + SystemConstant.AHAV_DOMAIN + "&product=" + SystemConstant.QIYE_PRODUCT + "&time="
                + time;
        sign = RSASignatureToQiye.generateSigature(SystemConstant.PRI_KEY, sign);
        url = url + "?" + "domain=" + SystemConstant.AHAV_DOMAIN + "&product=" + SystemConstant.QIYE_PRODUCT + "&sign="
                + sign + "&time=" + time + "&ver=" + unitVersion;

        String res = new HttpPost().post(url);
        JSONObject apiResult = JSONObject.parseObject(res);
        if (!apiResult.getBooleanValue("suc")) {
            return null;
        }

        return apiResult;
    }

}
