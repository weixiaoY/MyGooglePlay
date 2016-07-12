package com.itheima.googleplay25.base;

import android.app.Application;

import com.itheima.googleplay25.util.UiUtil;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.base
 *  @文件名:   GooglePlayApplication
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/7 22:28
 *  @描述：    TODO
 */
public class GooglePlayApplication extends Application{
    private static final String TAG = "GooglePlayApplication";

    @Override
    public void onCreate() {
        //启动应用第一时间调用这些,初始化一些工具,日志,性能检测,用户数据统计工具
        super.onCreate();

        UiUtil.init(this);
    }
}
