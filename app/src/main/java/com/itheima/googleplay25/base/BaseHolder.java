package com.itheima.googleplay25.base;

import android.view.View;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.base
 *  @文件名:   BaseHolder
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/12 18:39
 *  @描述：    TODO
 */
public abstract class BaseHolder<T> {
    private static final String TAG = "BaseHolder";

    //public  abstract void inflateAndFinView();

    public abstract View inflateAndFindView();

   // public abstract void findView();

    public abstract  void setData(T data);

    //需要支持更多更复杂的布局,抽取成一个类

}
