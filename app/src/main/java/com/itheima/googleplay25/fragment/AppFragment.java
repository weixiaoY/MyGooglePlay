package com.itheima.googleplay25.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.ListView;

import com.itheima.googleplay25.base.BaseHolder;
import com.itheima.googleplay25.base.LoadDataFragment;
import com.itheima.googleplay25.base.SuperBaseAdapter;
import com.itheima.googleplay25.holder.AppHolder;
import com.itheima.googleplay25.util.UiUtil;
import com.itheima.googleplay25.view.LoadDataView;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.fragment
 *  @文件名:   AppFragment
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/8 20:10
 *  @描述：    AppFragment Fragment的显示类
 */
public class AppFragment extends LoadDataFragment {
    private static final String TAG = "AppFragment";
    private List<String> mData;


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

    private class AppAdapter extends SuperBaseAdapter{

        public AppAdapter(List data) {
            super(data);
        }

        @Override
        protected List getMoreData()
                throws Exception
        {
            return null;
        }


        @Override
        protected BaseHolder getBaseHolder() {
            return new AppHolder();
        }
    }

    @Override
    protected LoadDataView.Result doInBackground()
    {
        mData = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mData.add("抽取后的App数据<<<<" + i);
           // System.out.println(">>>>>>>>>>" + mData);
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
