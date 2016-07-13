package com.itheima.googleplay25.protocol;

import com.google.gson.Gson;
import com.itheima.googleplay25.beans.HomeBean;
import com.itheima.googleplay25.util.Constans;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.Map;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.protocol
 *  @文件名:   BaseProtocol
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/13 23:51
 *  @描述：    通用协议
 */
public abstract class BaseProtocol {
    private static final String TAG = "BaseProtocol";

    Map<String ,String> map ;

    public BaseProtocol(Map<String, String> map) {
        this.map = map;
    }


    public HomeBean loadData(String index) throws Exception{
        OkHttpClient client = new OkHttpClient();       //创建okHttp类
        String       url = getUrl();
        //Log.e("HomeAdaper", url);
        //请求数据   get方法,url地址 ,建立连接
        Request request = new Request.Builder().get().url(url).build();
        Response response = client.newCall(request)
                                  .execute();//直接执行同步执行
        if (response.isSuccessful()){       //这个方法是返回值200-300就是成功
            final String json = response.body()
                                        .string();
            Gson     gson     = new Gson();
            HomeBean homeBean = gson.fromJson(json, HomeBean.class);
            if (homeBean != null){          //如果有数据就添加到集合
                return homeBean;
            }
        }
        return null;
    }

    private String getUrl() {
        //http://10.0.2.2:8080/home?index=0
        String url = Constans.URL;
        url += getInterface();
        if (map != null){
            url += "?";
            for(Map.Entry<String,String> entry : map.entrySet()){
                String key = entry.getKey();
                url += key;
                url += "=";
                url += "&";
            }
            //去掉最后一个&
            url = url.substring(0,url.length() -1);
        }

        return url;
    }

    protected abstract String getInterface();


}
