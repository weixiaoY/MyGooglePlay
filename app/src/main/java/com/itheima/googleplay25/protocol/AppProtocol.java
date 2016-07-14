package com.itheima.googleplay25.protocol;

import com.itheima.googleplay25.beans.HomeBean;

import java.util.List;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.protocol
 *  @文件名:   AppProtocol
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/14 10:03
 *  @描述：    TODO
 */
public class AppProtocol extends BaseProtocol<List<HomeBean.ListBean>> {
    private static final String TAG = "AppProtocol";

    @Override
    protected String getInterface() {
        return "app";
    }
}
