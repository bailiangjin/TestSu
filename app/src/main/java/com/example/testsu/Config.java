package com.example.testsu;

import com.kevin.baselibrary.utils.FilePathUtil;

import java.io.File;

/**
 *  配置类
 * 作者：bailiangjin  bailiangjin@gmail.com
 * 创建时间：16/3/14 22:24
 */
public class Config {


    /**
     * assets 目录下的apk文件名
     */
    public static final String APK_FILE_NAME ="innerApp.apk";

    /**
     * 临时APK文件存放路径
     */
    public static final String APK_FILEPATH = FilePathUtil.getAppPath() + File.separator + APK_FILE_NAME;

    /**
     * 被启动应用的intent action
     */
    public static final String ACTION = "com.kevin.innerapp";

    /**
     * 被启动应用的包名
     */
    public static final String PACKAGE_NAME = "com.kevin.innerapp";

    /**
     * 权限管理APP 应用包名数组
     */
    public static final String[] SU_APP_PACKAGE_NAME_ARRAY = {"com.noshufou.android.su","eu.chainfire.supersu"};

    /**
     * 安全App 应用包名数组
     */
    public static final String[] SAFE_APP_PACKAGE_NAME_ARRAY = {"com.qihoo360.mobilesafe","com.lbe.security"};
}
