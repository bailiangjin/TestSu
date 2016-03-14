package com.example.testsu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kevin.baselibrary.utils.AssetUtils;
import com.kevin.baselibrary.utils.FilePathUtil;
import com.kevin.baselibrary.utils.ToastUtils;

import java.io.File;
import java.io.IOException;

public class MainActivity extends Activity {
    private String apkFilePath = "";
    private String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apkFilePath = FilePathUtil.getSdcardPath() + File.separator + "innerApp.apk";

        new Thread(new Runnable() {
            @Override
            public void run() {
                AssetUtils.copyAssetFiles(MainActivity.this, "innerApp.apk", apkFilePath);
            }
        }).start();

        try {
            Runtime.getRuntime().exec("su");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void click1(View view) {

        ToastUtils.shortShow(this,"禁用SuperSU");
            // List<String> results = RootTools.sendShell(
            // "pm disable com.qihoo360.mobilesafe", 30000);
            // System.out.println(results.get(0));
            // List<String> results1 = RootTools.sendShell(
            // "pm disable eu.chainfire.supersu", 30000);
            // System.out.println(results1.get(0));
            // List<String> results2 = RootTools.sendShell(
            // "pm disable com.noshufou.android.su", 30000);
            // System.out.println(results2.get(0));
            // List<String> results3 = RootTools.sendShell(
            // "pm disable co.lvdou.superuser", 30000);
            // System.out.println(results3.get(0));
            // List<String> results4 = RootTools.sendShell(
            // "pm disable com.mgyun.shua.su", 30000);
            // System.out.println(results4.get(0));
            RootUtils.disableApp("com.noshufou.android.su");
            RootUtils.disableApp("eu.chainfire.supersu");
    }

    public void click2(View view) {
        ToastUtils.shortShow(this,"启用SuperSU");
            // List<String> results = RootTools.sendShell(
            // "pm enable com.qihoo360.mobilesafe", 30000);
            // System.out.println(results.get(0));
            // List<String> results1 = RootTools.sendShell(
            // "pm enable eu.chainfire.supersu", 30000);
            // System.out.println(results1.get(0));
            // List<String> results2 = RootTools.sendShell(
            // "pm enable com.noshufou.android.su", 30000);
            // System.out.println(results2.get(0));
            // List<String> results3 = RootTools.sendShell(
            // "pm enable co.lvdou.superuser", 30000);
            // System.out.println(results3.get(0));
            // List<String> results4 = RootTools.sendShell(
            // "pm enable com.mgyun.shua.su", 30000);
            // System.out.println(results4.get(0));
            RootUtils.enableApp("com.noshufou.android.su");
            RootUtils.enableApp("eu.chainfire.supersu");

    }

    public void click3(View view) {
        ToastUtils.shortShow(this,"禁用LBE");
        RootUtils.disableApp("com.lbe.security");

    }

    public void click4(View view) {
        ToastUtils.shortShow(this,"启用LBE");
        RootUtils.enableApp("com.lbe.security");
    }

    public void click5(View view) {

        Toast.makeText(MainActivity.this, "禁用360", Toast.LENGTH_SHORT).show();

        RootUtils.disableApp("com.qihoo360.mobilesafe");

    }

    public void click6(View view) {

        Toast.makeText(MainActivity.this, "启用360", Toast.LENGTH_SHORT).show();

        RootUtils.enableApp("com.qihoo360.mobilesafe");


    }

    public void click7(View view) {

        Toast.makeText(MainActivity.this, "安装应用", Toast.LENGTH_SHORT).show();

        RootUtils.installApk(apkFilePath);

        new File(apkFilePath).delete();
    }

    public void click8(View view) {

        String action = Config.action;
        Toast.makeText(MainActivity.this, "打开应用", Toast.LENGTH_SHORT).show();
        boolean isSuccess = CommonUtils.openApp(MainActivity.this, action);
        Log.d(TAG,"open " + action + ":" + isSuccess);


    }



}
