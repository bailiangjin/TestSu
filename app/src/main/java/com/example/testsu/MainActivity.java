package com.example.testsu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kevin.baselibrary.utils.ToastUtils;

import java.io.File;

public class MainActivity extends Activity {
    private String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //拷贝文件
        CommonUtils.copyApkFile(this, Config.APK_FILEPATH);

        RootUtils.checkRootPermission();



    }

    public void click1(View view) {

        ToastUtils.shortShow(this, "禁用SuperSU");
        RootUtils.disableApp("com.noshufou.android.su");
        RootUtils.disableApp("eu.chainfire.supersu");
    }

    public void click2(View view) {
        ToastUtils.shortShow(this, "启用SuperSU");
        RootUtils.enableApp("com.noshufou.android.su");
        RootUtils.enableApp("eu.chainfire.supersu");

    }

    public void click3(View view) {
        ToastUtils.shortShow(this, "禁用LBE");
        RootUtils.disableApp("com.lbe.security");

    }

    public void click4(View view) {
        ToastUtils.shortShow(this, "启用LBE");
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

        RootUtils.installApk(Config.APK_FILEPATH);

        new File(Config.APK_FILEPATH).delete();
    }

    public void click8(View view) {

        String action = Config.ACTION;
        Toast.makeText(MainActivity.this, "打开内部应用", Toast.LENGTH_SHORT).show();
        boolean isSuccess = CommonUtils.openApp(MainActivity.this, action);
        Log.d(TAG, "open " + action + ":" + isSuccess);


    }

    public void click9(View view) {

        Toast.makeText(MainActivity.this, "卸载内部应用", Toast.LENGTH_SHORT).show();
        String packageName = Config.PACKAGENAME;
        boolean isSuccess = RootUtils.uninstallApk(packageName);
        Log.d(TAG, "卸载 " + packageName + ":" + isSuccess);


    }


}
