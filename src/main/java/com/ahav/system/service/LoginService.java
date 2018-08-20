package com.ahav.system.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.ahav.system.entity.SystemResult;
import com.netease.domainmail.RSATool;

/**
 * 登录服务
 * <br>类名：LoginService<br>
 * 作者： mht<br>
 * 日期： 2018年8月3日-下午2:01:02<br>
 */
public interface LoginService {
    /**
     * 系统登录
     * <br>作者： mht<br> 
     * 时间：2018年8月3日-下午2:23:38<br>
     * @param username
     * @param password
     * @return
     */
    SystemResult login(String username, String password);
    
    /**
     * 当前用户登出
     * <br>作者： mht<br> 
     * 时间：2018年8月3日-下午3:51:50<br>
     * @return
     */
    SystemResult logout();
    
    /**
     * 网易邮箱单点登录
     * <br>作者： mht<br> 
     * 时间：2018年8月20日-下午1:56:58<br>
     */
    default void ntesLogin(HttpServletResponse response) {
        // 测试账号,牟昊天的企业邮箱
        String account_name = "haotian.mou@ahav.com.cn";
        
        // 当前时间戳
        long currTime = System.currentTimeMillis();
        
        // 安恒网易企业邮箱域名，为啥域名登录前后会变化？mail.ahav.com.cn/owa/
        String domain = "mailhz.qiye.163.com";
        
        // 私钥
        String priKey = "30820277020100300d06092a864886f70d0101010500048202613082025d02010002818100c7f15f0a3609bf6bbba6742fb091c5ec8a80532dc24c043a8fd6073ab77cb03b27e9b327847c6c5c3db5286a7b0ed05d3df641a0a55a09311c41ee8777c0f4259df4450a0e9deb9d21c2edf23406038649b1533000c94c2c8b56e6f497def21c6665ee8391c363c68108a8112e8f2375bab2662b7a7f12fb5227cea5d4c3c725020301000102818100981c9ec482f6098669bdaa18fa08fcfe0724b7a3517549a64ff5718c8f39b24d5f0121c647b9ed2a83dbc5c2b49a41951e65b1ea08c225ca12086af2eefd81ff9ef4daa680a3df4e48f4c06c247f530fbd2b8e5a6110c339debb6117ca90535acf412e549a2b93c8896a5b869fc466d6c4bbbdb327ded49086d8b889b69653c5024100f58aada6be022147638e8ef910c96b0be7933a2f8cf491ac9a024a5019868f2a9c3af2202328e7eb81721f417e4c1030c1e859cec6bfd50da542e8e49d4930a3024100d0757ebee9994505c30932c6b119974bfb56d4ba9b227a7ad6d878aa14e489a47228691771bead4f68022b370757b7c84b20d6dc830559da5f6c80928dcdfd9702402462232f14f29dd9c52fc791b26216219273e7684a5ba7064b5fd18122f72459ad02b303fe11bea3cfef88201d45ca145f773d6f7d55c98d1712de0d9a1cd13f024100c16510268c500ed20ea4bcffcb1423e27116989b5b0bbcb7b414f6b54c56ce3fcb4d245a84c352c48588e590d2f61561f4194e5d1c0cddd4938cf52781f9a69702404db0812d6d1aeac23f732df6be11a7d1b2732fe2e4da029cebc1054c699985fdda2a5964fda4985072f1a2d3f6e2028e65bd29ca307097e6fca68affeb433441";
        
        // 要加密的信息
        String src = account_name + domain + currTime;
        
        RSATool rsa = new RSATool();
        //加密串 (摘要)
        String enc = rsa.generateSHA1withRSASigature(src, priKey);
        
        //提交登录的url,后台加上必须的参数,为了安全，可使用https提交
        String url = "https://entryhz.qiye.163.com/domain/oa/Entry?domain=" + domain + "&account_name=" + account_name + "&time=" + currTime + "&enc=" + enc/* + "& language =" + language*/;
        
        //登录,也可以采用form表单post提交的方式。
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
