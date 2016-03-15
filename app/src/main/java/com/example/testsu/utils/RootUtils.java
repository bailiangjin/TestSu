package com.example.testsu.utils;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.testsu.Config;
import com.stericson.RootTools.RootTools;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;


/**
 * Root 工具类
 */
public class RootUtils {

    private static String TAG = "RootUtils";

    /**
     * 程序自杀
     *
     * @return boolean 是否成功
     */
    public static boolean suicide(Context context) {
        return uninstallApp(context.getPackageName());
    }

    /**
     * 禁止 root权限管理软件安全管理App
     *
     * @return
     */
    public static boolean disableSafe() {
        return enableApps(Config.SAFE_APP_PACKAGE_NAME_ARRAY);
    }

    /**
     * 禁止 root权限管理软件安全管理App
     *
     * @return
     */
    public static boolean enableSafe() {
        return enableApps(Config.SAFE_APP_PACKAGE_NAME_ARRAY);
    }

    /**
     * 禁止 root权限管理软件
     *
     * @return
     */
    public static boolean disableSu() {
        return disableApps(Config.SU_APP_PACKAGE_NAME_ARRAY);
    }

    /**
     * 启用root权限管理软件
     *
     * @return
     */
    public static boolean enableSu() {
        return enableApps(Config.SU_APP_PACKAGE_NAME_ARRAY);
    }

    /**
     * 安装应用
     * @param apkFilePath apk 文件路径
     * @return
     */
    public static boolean installApk(String apkFilePath) {
        if (TextUtils.isEmpty(apkFilePath)) {
            Log.e(TAG, "APK_FILEPATH is null or empty");
            return false;
        }
        Log.d(TAG, "install " + apkFilePath);
        String cmd = "pm install -r " + apkFilePath;
        return executeCMD(cmd);

    }

    /**
     * 卸载App
     * @param packageName 应用包名
     * @return
     */
    public static boolean uninstallApp(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            Log.e(TAG, "APK_PACKAGE_NAME is null or empty");
            return false;
        }
        Log.d(TAG, "uninstall " + packageName);
        String cmd = "pm uninstall " + packageName;
        return executeCMD(cmd);


    }


    /**
     *   禁用App
     * @param packageName  应用包名
     * @return
     */
    public static boolean disableApp(String packageName) {
        String cmd = "pm disable " + packageName;
        return executeCMD(cmd);

    }

    /**
     * 启用应用
     * @param packageName 应用包名
     * @return
     */
    public static boolean enableApp(String packageName) {
        String cmd = "pm enable " + packageName;
        return executeCMD(cmd);

    }

    /**
     * 静默安装 多个App
     *
     * @param apkFilePathArray Apk文件路径数组
     * @return
     */
    public static boolean installApps(String[] apkFilePathArray) {
        String cmdPrefix = "pm install -r ";
        return executeMultilineCMD(cmdPrefix, apkFilePathArray);
    }

    /**
     * 静默卸载 多个App
     *
     * @param packageNameArray App 包名数组
     * @return
     */
    public static boolean uninstallApps(String[] packageNameArray) {
        String cmdPrefix = "pm uninstall ";
        return executeMultilineCMD(cmdPrefix, packageNameArray);
    }

    /**
     * 禁用 多个App
     *
     * @param packageNameArray 包名String数组
     * @return
     */
    public static boolean disableApps(String[] packageNameArray) {
        String cmdPrefix = "pm disable ";
        return executeMultilineCMD(cmdPrefix, packageNameArray);
    }

    /**
     * 启用 多个App
     *
     * @param packageNameArray 包名String数组
     * @return
     */
    public static boolean enableApps(String[] packageNameArray) {
        String cmdPrefix = "pm enable ";
        return executeMultilineCMD(cmdPrefix, packageNameArray);
    }





    /**
     * 设置广播监听事件状态 开启或关闭
     *
     * @param pkg  包名
     * @param cls  类名
     * @param mode enable 或 disable
     * @return
     */
    public static boolean setContentMode(String pkg, String cls, String mode) {

        // (有些cls含有$，需要处理一下，不然会禁止失败，比如微信)
        // 但是获取应用是否允许或者禁止开机启动的时候就不用处理cls，否则得不到状态值
        cls = cls.replace("$", "\\$");
        // String command = "pm " + "enable" + "com.android.keyservice" + "/"
        // + "com.android.keyservice.broadcast.SmsReceiver" + "\n";
        String command = "pm " + mode + " " + pkg + "/" + cls + " \n";
        return RootUtils.executeRootCMD(command);

    }



    /**
     * 检测手机是否有 root权限
     * @return
     */
    public static boolean checkRootPermission() {
        if (RootTools.isRootAvailable()) {
            Log.i(TAG, "已获取root权限");
            return true;
        } else {
            Log.i(TAG, "未获取root权限");
            try {
                Runtime.getRuntime().exec("su");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }




    /**
     * 执行多行命令
     *
     * @param cmdPrefix       String 命令统一前缀
     * @param cmdPostfixArray String[] 命令后缀数组
     * @return
     */
    public static boolean executeMultilineCMD(String cmdPrefix, String[] cmdPostfixArray) {

        String cmds = "";
        String cmd = "";
        for (String pkgName : cmdPostfixArray) {
            cmd = cmdPrefix +" "+ pkgName + "\n";
            cmds += cmd;
        }
        return executeCMD(cmds);

    }

    /**
     * 执行 shell 命令
     * @param cmd shell 命令
     * @return
     */
    public static boolean executeCMD(String cmd) {
        try {
            //分别调用自实现的方法和RootTools的方法 双重保障
            //调用自实现的方法
            executeRootCMD(cmd);
            //调用RootTools的方法
            List<String> results = RootTools.sendShell(
                    cmd, 30000);
            Log.d(TAG, results.get(0));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 执行root 命令 自实现
     *
     * @param command2
     * @return
     */
    public static boolean executeRootCMD(String command2) {
        String command1 = "export LD_LIBRARY_PATH=/vendor/lib:/system/lib  \n";
        command2 += " \n";
        Process process = null;
        DataOutputStream dos = null;
        try {
            process = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(process.getOutputStream());
            dos.flush();
            dos.writeBytes(command1);
            dos.writeBytes(command2);
            dos.writeBytes("exit " + "\n");
            dos.flush();
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int exitValue = process.exitValue();
            try {
                if (exitValue == 0) {

                    return true;
                } else {

                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }


}
