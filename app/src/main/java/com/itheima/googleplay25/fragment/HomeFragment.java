package com.itheima.googleplay25.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.itheima.googleplay25.base.BaseHolder;
import com.itheima.googleplay25.base.LoadDataFragment;
import com.itheima.googleplay25.base.SuperBaseAdapter;
import com.itheima.googleplay25.holder.HomeHolder;
import com.itheima.googleplay25.util.Constans;
import com.itheima.googleplay25.util.UiUtil;
import com.itheima.googleplay25.view.LoadDataView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.fragment
 *  @文件名:   HomeFragment
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/8 20:08
 *  @描述：    HomeFragment
 */
public class HomeFragment extends LoadDataFragment {
    private static final String TAG = "HomeFragment";

    private List<String> mData;


    @Override
    protected View onInitSuccessView() {
        //使用listview形式
        ListView lv = new ListView(UiUtil.getContext());
        lv.setBackgroundColor(Color.parseColor("#cccccc"));
        //lv.setDividerHeight(0);   4.0以上需要加上

        lv.setAdapter(new HomeAdaper(mData));

       /* TextView tv = new TextView(UiUtil.getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(25);
        tv.setTextColor(Color.BLACK);
        tv.setText("来自网络的首页");*/
        return lv;
    }

    private class HomeAdaper extends SuperBaseAdapter {
        //通过构造方法传递参数到SuperBaseAdapter;
        public HomeAdaper(List data) {
            super(data);
        }

        //通过继承返回BaseHolder
        @Override
        protected BaseHolder getBaseHolder() {
            return new HomeHolder();
        }
    }

    /*******************************/

    @Override
    protected LoadDataView.Result doInBackground() {
        //3,真实服务器数据

        // 使用okHttp组件
            // 1,传输效率高使用GZIP,
            // 支持新的IPv6,
            // 支持spdy服务器缓存技术
        OkHttpClient client = new OkHttpClient();       //创建okHttp类
        String url = Constans.URL + "home?index=0";
        //请求数据                get方法,url地址 ,建立连接
        Request request = new Request.Builder().get().url(url).build();

        try {
            Response response = client.newCall(request)
                                     .execute();//直接执行同步执行
            if (response.isSuccessful()){       //这个方法是返回值200-300就是成功
                final String body = response.body()
                                            .string();
                //System.out.println(">>>>>>" + body);
                UiUtil.post(new Runnable() {    //这里是直接调用了handler的post方法来在界面显示.
                                                //如果直接打log可能会取不到数据,这点没有eclipse好用
                    @Override
                    public void run() {
                        Toast.makeText(UiUtil.getContext(), body, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*//异步执行
        client.newCall(request).enqueue(new Callback() {
            @Override       //
            public void onFailure(Request request, IOException e) {

            }

            @Override   //获取服务器返回的方法.
            public void onResponse(Response response)
                    throws IOException
            {

            }
        });*/





        //使用模拟数据
      /*  mData = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            mData.add("抽取后的数据<<<<" + i);
           // System.out.println("" + mData);
        }*/


        /*try {
            Log.d(TAG, "耗时操作");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return LoadDataView.Result.SUCCESS;
    }


   /* @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        TextView tv = new TextView(UiUtil.getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(25);
        tv.setTextColor(Color.BLACK);
        tv.setText("首页");
        return ;
    }*/
}
