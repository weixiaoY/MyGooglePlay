package com.itheima.googleplay25.holder;

import android.view.View;
import android.widget.TextView;

import com.itheima.googleplay25.R;
import com.itheima.googleplay25.base.BaseHolder;
import com.itheima.googleplay25.util.UiUtil;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.base
 *  @文件名:   HomeHolder
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/12 18:50
 *  @描述：    TODO
 */
public class HomeHolder extends BaseHolder<String> {


    private static final String TAG = "HomeHolder";
    private View mConvertView;
    TextView tv;

    @Override
    public View inflateAndFindView() {
        mConvertView = View.inflate(UiUtil.getContext(), R.layout.item_home, null);
        tv = (TextView) mConvertView.findViewById(R.id.tv__item_home);
        return mConvertView;
    }

    @Override
    public void setData(String data) {
        tv.setText(data);
    }
}
