package com.itheima.googleplay25.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.ListView;

import com.itheima.googleplay25.base.BaseHolder;
import com.itheima.googleplay25.base.LoadDataFragment;
import com.itheima.googleplay25.base.SuperBaseAdapter;
import com.itheima.googleplay25.beans.HomeBean;
import com.itheima.googleplay25.holder.HomeHolder;
import com.itheima.googleplay25.holder.TopPicHolder;
import com.itheima.googleplay25.protocol.HomeProtocol;
import com.itheima.googleplay25.util.UiUtil;
import com.itheima.googleplay25.view.LoadDataView;

import java.util.List;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.fragment
 *  @文件名:   HomeFragment
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/8 20:08
 *  @描述：    HomeFragment
 */
public class HomeFragment
        extends LoadDataFragment
{
    private static final String TAG = "HomeFragment";

    private List<HomeBean.ListBean> mData;
    private List<String>            mPicture;
    private HomeProtocol            mProtocol;

    @Override
    protected View onInitSuccessView() {
        //使用listview形式
        ListView lv = new ListView(UiUtil.getContext());
        lv.setBackgroundColor(Color.parseColor("#cccccc"));
        //lv.setDividerHeight(0);   4.0以上需要加上
        //使用通过holder来生成view
        TopPicHolder topPicHolder = new TopPicHolder();
        View         picview         = topPicHolder.inflateAndFindView();
        topPicHolder.setData(mPicture);

        lv.addHeaderView(picview);
        lv.setAdapter(new HomeAdaper(mData));

       /* TextView tv = new TextView(UiUtil.getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(25);
        tv.setTextColor(Color.BLACK);
        tv.setText("来自网络的首页");*/
        return lv;
    }

    private class HomeAdaper
            extends SuperBaseAdapter
    {


        @Override
        public boolean isSupportLoadMore() {
            return true;
        }

        //通过构造方法传递参数到SuperBaseAdapter;
        public HomeAdaper(List data) {
            super(data);
        }

        @Override
        protected List getMoreData()
                throws Exception
        {
            //3,真实服务器数据
            // 使用okHttp组件
            // 1,传输效率高使用GZIP,
            // 支持新的IPv6,
            // 支持spdy服务器缓存技术

            //4个版本.使用首页协议
            mProtocol = new HomeProtocol();
            HomeBean homeBean = mProtocol.loadData(mData.size() + "");
            if (homeBean != null) {
                return homeBean.list;
            }
            return null;
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
        //4,第4个版本使用首页协议
        //4个版本.使用首页协议
        mProtocol = new HomeProtocol();
        try {
            HomeBean homeBean = mProtocol.loadData("0");
            if (homeBean != null) {
                mData = homeBean.list;
                mPicture = homeBean.picture;
            } else {
                return LoadDataView.Result.EMPTY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return LoadDataView.Result.ERROR;
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
}