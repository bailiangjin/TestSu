package com.example.testsu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.testsu.Config;
import com.kevin.baselibrary.utils.AssetUtils;
import com.kevin.baselibrary.utils.CleanUtils;
import com.kevin.baselibrary.utils.ToastUtils;

import java.io.File;

/**
 * 作者：bailiangjin  bailiangjin@gmail.com
 * 创建时间：16/3/14 22:19
 */
public class CommonUtils {

    /**
     * 拷贝并安装Apk
     * @param context
     */
    public static void copyAndInstallApk(final Context context) {
        new AsyncTask<Void, Void, Void>() {
            /**
             * 在异步的后台任务执行之前调用的方法 这个方法是运行在主线程里面的.
             */
            @Override
            protected void onPreExecute() {
                //TODO:
                ToastUtils.show("正在安装");
                super.onPreExecute();
            }
            /**
             * 在异步的后台任务执行之后调用的方法 这个方法是运行在主线程里面的.
             */
            @Override
            protected void onPostExecute(Void result) {
                //TODO:
                ToastUtils.show("安装成功");
                super.onPostExecute(result);
            }
            /**
             * 在后台执行的方法,方法是运行在子线程里面的
             */
            @Override
            protected Void doInBackground(Void... params) {
                CommonUtils.copyApkFile(context, Config.APK_FILE_NAME, Config.APK_FILEPATH);
                RootUtils.installApk(Config.APK_FILEPATH);

                new File(Config.APK_FILEPATH).delete();
                return null;
            }
        }.execute();
    }

    /**
     * 清理数据 重启安全权限软件 卸载应用
     *
     * @param context Context
     * @return
     */
    public static boolean cleanDataAndSuicide(Context context) {
        boolean isSuccess = true;
        //清理数据
        CleanUtils.cleanAppRootFilePath();
        //TODO:恢复安全软件
        //TODO:恢复su软件
        //卸载自身 自杀式
        return RootUtils.suicide(context);
    }


    /**
     * 拷贝Apk文件
     *
     * @param context     Context
     * @param apkFilePath 要拷贝的Apk文件路径
     * @return
     */
    public static boolean copyApkFile(final Context context, final String apkFileName, final String apkFilePath) {
        return AssetUtils.copyAssetFiles(context, apkFileName, apkFilePath);
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
