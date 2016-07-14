package com.itheima.googleplay25.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.ListView;

import com.itheima.googleplay25.base.BaseHolder;
import com.itheima.googleplay25.base.LoadDataFragment;
import com.itheima.googleplay25.base.SuperBaseAdapter;
import com.itheima.googleplay25.beans.HomeBean;
import com.itheima.googleplay25.holder.HomeHolder;
import com.itheima.googleplay25.protocol.AppProtocol;
import com.itheima.googleplay25.util.UiUtil;
import com.itheima.googleplay25.view.LoadDataView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.fragment
 *  @文件名:   AppFragment
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/8 20:10
 *  @描述：    AppFragment Fragment的显示类
 */
public class AppFragment
        extends LoadDataFragment
{
    private static final String TAG = "AppFragment";
    private List<HomeBean.ListBean> mData;


    @Override
    protected View onInitSuccessView() {

        ListView lv = new ListView(UiUtil.getContext());
        lv.setBackgroundColor(Color.parseColor("#cccccc"));
        //lv.setDividerHeight(0);   4.0以上需要加上

        lv.setAdapter(new AppAdapter(mData));
        /*TextView tv = new TextView(UiUtil.getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(25);
        tv.setTextColor(Color.BLACK);
        tv.setText("来自网络的app页面");*/
        return lv;
    }

    private class AppAdapter
            extends SuperBaseAdapter
    {

        public AppAdapter(List data) {
            super(data);
        }

        @Override
        public boolean isSupportLoadMore() {    ///允许上来加载更多
            return true;
        }

        @Override
        protected List getMoreData()
                throws Exception {
            AppProtocol         protocol = new AppProtocol();
            Map<String, String> map      = new HashMap<>();
            map.put("index", String.valueOf(mData.size()));
            protocol.setMap(map);
            return protocol.loadData();
        }


        @Override
        protected BaseHolder getBaseHolder() {
            return new HomeHolder();
        }
    }

    @Override
    protected LoadDataView.Result doInBackground()
    {
        //使用通用协议
        AppProtocol         protocol = new AppProtocol();
        Map<String, String> map      = new HashMap<>();
        map.put("index", "0");
        protocol.setMap(map);
        try {
            mData = protocol.loadData();
            if (mData == null || mData.size() == 0) {
                return LoadDataView.Result.EMPTY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return LoadDataView.Result.ERROR;
        }
        /*try {
            Log.d(TAG, "耗时操作");
            Thread.sleep(10000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return LoadDataView.Result.SUCCESS;
    }

    /*public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        TextView tv = new TextView(UiUtil.getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(25);
        tv.setTextColor(Color.BLACK);
        tv.setText("app页面");
        return tv;
    }*/

}
