package com.itheima.googleplay25.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.googleplay25.util.UiUtil;
import com.itheima.googleplay25.view.LoadDataView;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.base
 *  @文件名:   LoadDataFragment
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/8 20:39
 *  @描述：    网络连接的基类
 */
public abstract class LoadDataFragment extends BaseFragment {
    private static final String TAG = "LoadDataFragment";
    private LoadDataView mLoadDataView;


    //viewPage销毁后会重新执行生命周期
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //处理三种情况的页面     非空判断,解决重复加载的问题
        if (mLoadDataView == null){
            mLoadDataView = new LoadDataView(UiUtil.getContext()) {
                @Override
                protected View onLoadSuccess() {
                    //再交给具体的子类
                    return onInitSuccessView();
                }
                @Override
                protected Result getDataFromServer() {
                    //当前类也是基类,无法做统一处理方案,继续抛给子类实现
                    return doInBackground();
                }
            };
        }
        return mLoadDataView;
    }

    protected abstract View onInitSuccessView();

    protected abstract LoadDataView.Result doInBackground();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "新建的activity");
        super.onActivityCreated(savedInstanceState);
       /* //在挂载到activity之后发起网络请求,把请求任务交给loadDataView
        if (mLoadDataView != null) {
            mLoadDataView.loadData();
        }*/
    }

    public void loadData(){
        if (mLoadDataView != null){
            mLoadDataView.loadData();
        }
    }
}
