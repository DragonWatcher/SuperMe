package com.ahav.system.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha1Hash;

import com.ahav.system.config.shiro.UserRealm;


/**
 * 加密工具类 <br>
 * 类名：Encrypt<br>
 * 作者： mht<br>
 * 日期： 2018年8月3日-下午8:41:47<br>
 */
public class Encrypt {

    /**
     * 系统用户，密码生成算法 <br>
     * 作者： mht<br>
     * 时间：2018年8月3日-下午8:56:52<br>
     * 
     * @param username
     * @param trueName
     * @param password
     * @return
     */
    public static String[] encryptPassword(String username, String trueName, String password) {
        String sha1Salt = genSalt(username, trueName);

        String hashedPwd = encryptPassword(password, sha1Salt);
        String[] hashedCredentials = { hashedPwd, sha1Salt };
        return hashedCredentials;
    }
    
    /**
     * 加盐加密
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午8:37:39<br>
     * @param password
     * @param salt
     * @return
     */
    public static String encryptPassword(String password, String salt) {
        Md5Hash hashedPwd = new Md5Hash(password, salt, UserRealm.HASH_ITERATIONS);
        return hashedPwd.toString();
    }

    /**
     * 密码盐生成算法 <br>
     * 作者： mht<br>
     * 时间：2018年8月3日-下午8:51:01<br>
     * 
     * @param username
     * @param trueName
     * @return
     */
    private static String genSalt(String username, String trueName) {
        int hashIterations = UserRealm.HASH_ITERATIONS;
        // 使用username + trueName的指定散列次数后的值作为盐值
        String unhashedSalt = username;
        if (trueName != null)
            unhashedSalt = username + trueName;
        // 盐值采用sha1散列方式
        Sha1Hash finalSalt = new Sha1Hash(unhashedSalt, null, hashIterations);

        return finalSalt.toString();
    }

}
