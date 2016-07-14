package com.itheima.googleplay25.protocol;

import android.util.Log;

import com.google.gson.Gson;
import com.itheima.googleplay25.util.Constans;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.protocol
 *  @文件名:   BaseProtocol
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/13 23:51
 *  @描述：    通用协议
 */
public abstract class BaseProtocol<T> {
    private static final String TAG = "BaseProtocol";

    Map<String ,String> map ;

    public void setMap(Map<String ,String> map){
        this.map = map;
    }


    public T loadData() throws Exception{
        OkHttpClient client = new OkHttpClient();       //创建okHttp类
        String       url = getUrl();
        Log.e(TAG, url);
        //Log.e("HomeAdaper", url);
        //请求数据   get方法,url地址 ,建立连接
        Request request = new Request.Builder().get().url(url).build();
        Response response = client.newCall(request)
                                  .execute();//直接执行同步执行
        if (response.isSuccessful()){       //这个方法是返回值200-300就是成功
            final String json = response.body()
                                        .string();
            Log.e(TAG, json);
            Gson     gson     = new Gson();

            //泛型解析,支持多个页面
            //1.拿到具体实现类的class
           ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            Type[]  types = parameterizedType.getActualTypeArguments();//拿到具体实现类的type
            T t = gson.fromJson(json, types[0]);      //fromJson 方法可以传两种类型,源码

            if (t != null){          //如果有数据就添加到集合
                return t;
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
                String value = entry.getValue();
                url += value;
                url += "&";
            }
            //去掉最后一个&
            url = url.substring(0,url.length() -1);
        }

        return url;
    }

    protected abstract String getInterface();


}
