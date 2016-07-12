package com.itheima.googleplay25.beans;

import java.util.List;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.beans
 *  @文件名:   HomeBean
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/12 19:57
 *  @描述：    TODO
 */
public class HomeBean {
    private static final String TAG = "HomeBean";


    public List<String>   picture;
    /**
     * id : 1525490
     * name : 有缘网
     * packageName : com.youyuan.yyhl
     * iconUrl : app/com.youyuan.yyhl/icon.jpg
     * stars : 4
     * size : 3876203
     * downloadUrl : app/com.youyuan.yyhl/com.youyuan.yyhl.apk
     * des : 产品介绍：有缘是时下最受大众单身男女亲睐的婚恋交友软件。有缘网专注于通过轻松、
     */

    public List<ListBean> list;

    public static class ListBean {
        public int    id;
        public String name;
        public String packageName;
        public String iconUrl;
        public int    stars;
        public int    size;
        public String downloadUrl;
        public String des;
    }
}
