package com.example.testsu;

import com.kevin.baselibrary.utils.FilePathUtil;

import java.io.File;

/**
 * 作者：bailiangjin  bailiangjin@gmail.com
 * 创建时间：16/3/14 22:24
 */
public class Config {

    /**
     * 临时APK文件存放路径
     */
    public static final String APK_FILEPATH = FilePathUtil.getSdcardPath() + File.separator + "innerApp.apk";

    /**
     * 被启动应用的intent action
     */
    public static final String ACTION = "com.kevin.innerapp";

    /**
     * 被启动应用的包名
     */
    public static final String PACKAGENAME = "com.kevin.innerapp";
}
