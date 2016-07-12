package com.itheima.googleplay25.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.itheima.googleplay25.base.LoadDataFragment;
import com.itheima.googleplay25.util.UiUtil;
import com.itheima.googleplay25.view.LoadDataView;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.fragment
 *  @文件名:   AppFragment
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/8 20:10
 *  @描述：    TODO
 */
public class CategorFragment
        extends LoadDataFragment {
    private static final String TAG = "AppFragment";

    @Override
    protected View onInitSuccessView() {
        TextView tv = new TextView(UiUtil.getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(25);
        tv.setTextColor(Color.BLACK);
        tv.setText("来自网络的Category页面");
        return tv;
    }

    @Override
    protected LoadDataView.Result doInBackground()

    {
        try {
            Log.d(TAG, "耗时操作");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return LoadDataView.Result.ERROR;
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
