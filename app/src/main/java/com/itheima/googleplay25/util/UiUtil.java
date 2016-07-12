package com.itheima.googleplay25.util;

import android.content.Context;
import android.os.Handler;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.util
 *  @文件名:   UiUtil
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/7 22:33
 *  @描述：    TODO
 */
public class UiUtil {
    private static final String TAG = "UiUtil";
    private static Context mContext;

    private  static Handler handler;

    public static void init(Context googlePlayApplication) {
        mContext = googlePlayApplication;

        handler = new Handler();
    }

    public static Handler getHandler(){
        return handler;
    }

    public static Context getContext(){

       // mContext.startActivity(intent);
        return mContext;
    }

    //获取string-array
    public static String[] getStringArray(int resId){
        return mContext.getResources().getStringArray(resId);
    }

    //执行一个任务
    public static void post(Runnable runnable){
        handler.post(runnable);
    }
}
