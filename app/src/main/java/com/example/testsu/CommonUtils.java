package com.example.testsu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.kevin.baselibrary.utils.AssetUtils;

/**
 * 作者：bailiangjin  bailiangjin@gmail.com
 * 创建时间：16/3/14 22:19
 */
public class CommonUtils {


    /**
     * 拷贝Apk文件
     * @param context Context
     * @param apkFilePath 要拷贝的Apk文件路径
     * @return
     */
    public static boolean copyApkFile (final Context context,final String apkFilePath){
        try{

            new Thread(new Runnable() {
                @Override
                public void run() {
                    AssetUtils.copyAssetFiles(context, "innerApp.apk", apkFilePath);
                }

            }).start();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 打开应用
     * @param activity Activity
     * @param action intent 方式 打开其他应用的 action
     * @return
     */
    public static boolean openApp(Activity activity,String action){
        try{

            Intent intent = new Intent();
            intent.setAction(action);
            activity.startActivity(intent);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
