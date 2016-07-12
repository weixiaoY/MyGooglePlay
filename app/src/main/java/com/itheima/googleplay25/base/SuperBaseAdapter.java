package com.itheima.googleplay25.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.base
 *  @文件名:   SuperBaseAdapter
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/12 11:16
 *  @描述：    TODO
 */
public abstract class SuperBaseAdapter<T>
        extends BaseAdapter
{


    private static final String TAG = "SuperBaseAdapter";

    List<T> mData;

    public SuperBaseAdapter(List<T> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder homeHolder = null;
        if (convertView == null) {
            //1,创建holder
            homeHolder = getBaseHolder();
            //2.填充布局
            //convertView = View.inflate(UiUtil.getContext(), R.layout.item_home, null);
            convertView = homeHolder.inflateAndFindView();
            //3,查找控件
           // homeHolder.tv = (TextView) convertView.findViewById(R.id.tv__item_home);
          //  homeHolder.findView();

            //4,绑定holder和view
            convertView.setTag(homeHolder);
        } else {
            homeHolder = (BaseHolder)convertView.getTag();
        }
        //5,改view填充数据
        T data = mData.get(position);
       // homeHolder.tv.setText(data);
        homeHolder.setData(data);
        return convertView;
    }

    protected abstract BaseHolder getBaseHolder();


}
