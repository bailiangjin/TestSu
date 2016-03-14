package com.example.testsu;

import android.app.Activity;
import android.content.Intent;

/**
 * 作者：bailiangjin  bailiangjin@gmail.com
 * 创建时间：16/3/14 22:19
 */
public class CommonUtils {

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
