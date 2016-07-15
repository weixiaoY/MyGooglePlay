package com.itheima.googleplay25.protocol;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.itheima.googleplay25.util.Constans;
import com.itheima.googleplay25.util.MD5Utils;
import com.itheima.googleplay25.util.UiUtil;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    //缓存优化
    public T loadData() {
        T cach = getLocalData();
        if (cach != null){
            Log.e("cach" , "使用缓存");
            return cach;
        }
        Log.e("cach" , "使用新缓存");
        return getServerData();
    }

    private T getLocalData() {
        //取出缓存
        //1.找到缓存文件
        File file = getCacheFile();
        //2,读取文件内容
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            //TODO  使用机器效率更高的一行格式json
            String time = reader.readLine();    //拿到第一行存储的缓存过期时间
            //如果缓存时间+20秒,小于当前时间,就操作缓存过期
            if(Long.parseLong(time)+Constans.CACHE_OUT_TIME < System.currentTimeMillis()){
                Log.e(TAG, "缓存过期");
            }
            String json= reader.readLine();
            //3.解析成t类型
            T t = parserJson(json);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  null;
    }

    public File getCacheFile() {
        File dir;   //是否有sd卡,有就在sd卡data目录下创建
        if (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
            dir = new File(Environment.getExternalStorageDirectory(),"Android/data/" + UiUtil.getPackName() + "/json");
        }else{
            dir = new File(UiUtil.getContext().getCacheDir() , "json");
        }
        if(!dir.exists()){
            dir.mkdirs();   //创建多级目录   mkdir;
        }
        File file = new File(dir, MD5Utils.encode(getUrl()));
        return file;
    }

    private T getServerData() {

        OkHttpClient client = new OkHttpClient();       //创建okHttp类
        String    url    = getUrl();
        Log.e(TAG, url);
        //Log.e("HomeAdaper", url);
        //请求数据   get方法,url地址 ,建立连接

        Request request = new Request.Builder().get().url(url).build();
        Response response = null;
        try {
            response = client.newCall(request)
                             .execute();//直接执行同步执行
            if (response.isSuccessful()){       //这个方法是返回值200-300就是成功
                final String json = response.body()
                                            .string();
              //Log.e(TAG, json);
                T t = parserJson(json);
                //保存胆寒机器识别方便的json格式 2saveCache
                //把已经序列化的json数据,重新变成一行的json
                saveCache(new Gson().toJson(t));    //把已经序列化的json数据,重新变成一行的json
                if (t != null){          //如果有数据就添加到集合
                    return t;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void saveCache(String s) {
        File cache = getCacheFile();
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(cache));
            bw.write(System.currentTimeMillis() + "");  //缓存的时间,用于缓存过期操作
            bw.newLine();   //换行
            bw.write(s);
            bw.flush(); //把流管道中的数据全部提出
            Log.e("cache", "保存了缓存");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private T parserJson(String json) {Gson gson = new Gson();

        //泛型解析,支持多个页面
        //1.拿到具体实现类的class
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Type[]            types             = parameterizedType.getActualTypeArguments();//拿到具体实现类的type
        return gson.fromJson(json, types[0]);
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
