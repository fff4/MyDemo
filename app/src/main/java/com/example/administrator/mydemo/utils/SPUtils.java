package com.example.administrator.mydemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * sp 操作工具类
 */

public class SPUtils {
    private Context mContext;
    public static final String USER = "user";
    public static final String USER_KEY_NAME = "USER_KEY_NAME";
    public static final String USER_KEY_PWD = "USER_KEY_PWD";

    public SPUtils(Context context) {
        mContext = context;
    }

    /**
     * 保存账号密码
     * @param name 保存账号
     * @param pwd   密码
     */
    public void saveUser(String name, String pwd) {
        SharedPreferences sp = mContext.getSharedPreferences(USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(USER_KEY_NAME, name);
        edit.putString(USER_KEY_PWD, pwd);
        edit.commit();
    }

    /**
     * 查询保存的账号密码
     * @return
     */
    public Map<String, String> selUser() {
        SharedPreferences sp = mContext.getSharedPreferences(USER, Context.MODE_PRIVATE);
        String name = sp.getString(USER_KEY_NAME, null);
        String pwd = sp.getString(USER_KEY_PWD, null);
        Map<String, String> map = new HashMap<>();
        map.put(name, pwd);
        return map;
    }

    /**
     * 查询保存的账号密码
     * @return
     */
//    public Map<String, String> selUserAll() {
//        SharedPreferences sp = mContext.getSharedPreferences(USER, Context.MODE_PRIVATE);
//        Map<String, ?> all = sp.getAll();
//        return all;
//    }

}
