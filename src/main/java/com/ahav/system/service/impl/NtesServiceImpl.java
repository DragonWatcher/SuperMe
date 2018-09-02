package com.ahav.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

    private volatile JSONObject apiResult;

    private volatile Map<String, Dept> deptDBMap;

    private volatile SystemResult updDeptResult;

    @Override
    public SystemResult updLocalDepts() {
        // 1. 获取网易邮箱合作企业部门列表
        // 1.1 查询部门列表版本号
        Long unitVersionDB = deptDao.selectUnitDataVer(NtesDataVer.UNIT_VER);
        // 主线程处理逻辑门闩
        CountDownLatch dealResultsLatch = new CountDownLatch(2);

        /* 同步处理线程 */
        new Thread(() -> {
            try {
                dealResultsLatch.await(10, TimeUnit.SECONDS);
                if (apiResult == null) {
                    updDeptResult = new SystemResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "网易接口访问失败，请检查网络！",
                            null);
                } else if (deptDBMap == null) {
                    updDeptResult = new SystemResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "数据库访问失败，请检查网络！",
                            null);
                } else {
                    updDeptResult = processDeptTables(unitVersionDB);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        /* mysql查询线程 */
        new Thread(() -> {
            // 数据库中现有的全部部门List
            List<Dept> deptListDB = deptDao.allDepts();
            // deptDBMap必须在查询部门列表方法后初始化
            deptDBMap = new HashMap<>();
            if (deptListDB != null && deptListDB.size() != 0) {
                deptListDB.forEach((d) -> deptDBMap.put(d.getDeptId(), d));
            }
            dealResultsLatch.countDown();
        }).start();

        /* ntes接口调用线程 */
        new Thread(() -> {
            apiResult = getNtesData(NtesFunc.UNIT_GET_UNIT_LIST, unitVersionDB);
            dealResultsLatch.countDown();
        }).start();

        return updDeptResult;
    }

    @Override
    public SystemResult updLocalAccounts() {
        Long accountVer = deptDao.selectUnitDataVer(NtesDataVer.ACCOUNT_VER);
        // ntes接口调用
        JSONObject apiResult = getNtesData(NtesFunc.UNIT_GET_ACCOUNT_LIST, accountVer);

        return new SystemResult(HttpStatus.OK.value(), null, apiResult);
    }

    /**
     * 获取 ntes相关数据 <br>
     * 作者： mht<br>
     * 时间：2018年8月30日-下午4:38:33<br>
     * 
     * @param ntesFunc
     * @param dataVer
     * @return
     */
    private JSONObject getNtesData(NtesFunc ntesFunc, Long dataVer) {
        String url = SystemConstant.NTES_API_BASE_URL + ntesFunc.func();
        long time = System.currentTimeMillis();

        String sign = "domain=" + SystemConstant.AHAV_DOMAIN + "&product=" + SystemConstant.QIYE_PRODUCT + "&time="
                + time + (dataVer != null ? ("&ver=" + dataVer) : "");
        // 签名
        sign = RSASignatureToQiye.generateSigature(SystemConstant.PRI_KEY, sign);
        // url生成
        url = url + "?" + "domain=" + SystemConstant.AHAV_DOMAIN + "&product=" + SystemConstant.QIYE_PRODUCT + "&sign="
                + sign + "&time=" + time + (dataVer != null ? ("&ver=" + dataVer) : "");

        String res = new HttpPost().post(url);
        JSONObject apiResult = JSONObject.parseObject(res);

        return apiResult;
    }

    /**
     * 处理数据库部门表 <br>
     * 作者： mht<br>
     * 时间：2018年9月2日-下午1:05:10<br>
     * 
     * @param unitVersionDB
     * @return
     */
    private SystemResult processDeptTables(Long unitVersionDB) {
        // 1. 判断返回值是否正确
        if (!apiResult.getBooleanValue("suc")) {
            logger.info("网易邮箱接口请求错误码,error_code>>>" + apiResult.getString("error_code"));
            return new SystemResult(HttpStatus.OK.value(), "部门列表获取失败！", apiResult.getString("error_code"));
        }
        // 2. 更新数据库
        // 2.1 版本号一致，不需要更新数据库
        Long verFromNtes = apiResult.getLong("ver");
        if (verFromNtes.equals(unitVersionDB))
            return new SystemResult(HttpStatus.OK.value(), "部门列表已是最新", Boolean.TRUE);

        // 2.2 更新dept表中的数据
        JSONArray unitListArr = apiResult.getJSONArray("con");
        List<Dept> deptListNtes = new ArrayList<>();
        // unitListArr 转化为 List<Dept> deptList
        unitListArr.forEach((o) -> {
            JSONObject unit = JSONObject.parseObject(o.toString());
            deptListNtes.add(new Dept(unit.getString("unit_id"), unit.getString("unit_name"),
                    unit.getString("parent_id"), unit.getInteger("unit_rank"), null));
        });
        // deptDBMap中没有的id直接添加，有id的比较对象，有差异的更新
        deptListNtes.forEach((unit) -> {
            Dept deptDB = deptDBMap.get(unit.getDeptId());
            if (deptDB == null) {
                deptDao.insertDept(unit);
            } else {
                if (!unit.equals(deptDB))
                    deptDao.updateDept(unit);
            }
        });
        // 2.2.2 删除数据库中多余部门
        List<String> deptIdListNtes = new ArrayList<>();
        deptListNtes.forEach((d) -> deptIdListNtes.add(d.getDeptId()));

        Set<String> deptIdSetDB = deptDBMap.keySet();

        deptIdListNtes.forEach((unitId) -> {
            // 相同则移除数据库中的部门id列表，剩下的就是数据库中多余的部门id列表
            if (deptIdSetDB.contains(unitId))
                deptIdSetDB.remove(unitId);
        });
        // 执行（多余的）部门批量删除
        if (deptIdSetDB != null && deptIdSetDB.size() != 0) {
            deptDao.delDeptsBatch(deptIdSetDB);
        }

        // 3. 更新版本号
        new Thread(() -> deptDao.updateDataVer(NtesDataVer.UNIT_VER, verFromNtes)).start();

        return new SystemResult(HttpStatus.OK.value(), "部门信息更新成功！", Boolean.TRUE);
    }
}
