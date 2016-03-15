package com.example.testsu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.kevin.baselibrary.utils.AssetUtils;
import com.kevin.baselibrary.utils.FilePathUtil;
import com.kevin.baselibrary.utils.ToastUtils;
import com.kevin.javabaselib.utils.FileUtils;

/**
 * 作者：bailiangjin  bailiangjin@gmail.com
 * 创建时间：16/3/14 22:19
 */
public class CommonUtils {

    /**
     * 清理数据 重启安全权限软件 卸载应用
     *
     * @param context Context
     * @return
     */
    public static boolean cleanDataAndSuicide(Context context) {
        boolean isSuccess = true;
        //清理数据
        cleanData();
        //TODO:恢复安全软件
        //TODO:恢复su软件
        //卸载自身 自杀式
        return RootUtils.suicide(context);
    }

    public static boolean cleanData() {
        return FileUtils.deleteFile(FilePathUtil.getAppPath());
    }

    /**
     * 拷贝Apk文件
     *
     * @param context     Context
     * @param apkFilePath 要拷贝的Apk文件路径
     * @return
     */
    public static boolean copyApkFile(final Context context, final String apkFilePath) {
        try {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    AssetUtils.copyAssetFiles(context, "innerApp.apk", apkFilePath);
                }

            }).start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 打开应用
     *
     * @param activity Activity
     * @param action   intent 方式 打开其他应用的 action
     * @return
     */
    public static boolean openApp(Activity activity, String action) {
        try {

            Intent intent = new Intent();
            intent.setAction(action);
            activity.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
