package com.itheima.googleplay25.base;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.itheima.googleplay25.manager.ThreadManager;
import com.itheima.googleplay25.util.Constans;
import com.itheima.googleplay25.util.UiUtil;

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
    public static final int TYPE_LOAD_MORE = 0;
    public static final int TYPE_NORMAL = 1;

    //自然排序,0,1,2,自然递增
    @Override
    public int getItemViewType(int position) {
        //如果是最后一个
        if (position == getCount() - 1){
            return  TYPE_NORMAL;
        }
        return TYPE_LOAD_MORE;
    }

    //listview下增加一个
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    private static final String TAG = "SuperBaseAdapter";

    List<T> mData;

    public SuperBaseAdapter(List<T> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        if (mData != null) {       // 添加一个条目,用于上拉刷新
            return mData.size() + 1;
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

    //1,getView最后一次,position = getCount() - 1;  触发加载更多
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder homeHolder = null;
        if (convertView == null) {
            if (position == getCount() -1 ){
                //加载更多的情况,做一个特殊的holder
                // homeHolder = new LoadMoreHolder();
                homeHolder = getLoadMoreHolder();   //通过本类方法来创建LoadMoreHolder类对象,面向对象做法
            }else{
                //1,创建holder
                homeHolder = getBaseHolder();
            }

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
        if (position ==  getCount() -1 ){
            //加载更多的情况,设置ui和文本
           // int state = LoadMoreHolder.STATE_LODING_MORE; //测试数据
            //如果当前页面需要分页功能
            if (isSupportLoadMore()){
                loadMore(getLoadMoreHolder());  //加载更多更新状态方法
            }
           //homeHolder.setData(state);
        }else{
            T data = mData.get(position);
            // homeHolder.tv.setText(data);
            homeHolder.setData(data);
        }


        return convertView;
    }
    boolean isLoadingMore;
    private void loadMore(LoadMoreHolder loadMoreHolder) {
        if (isLoadingMore){
            return;
        }
        isLoadingMore = true;
        //加载更多的情况,更新状态
        loadMoreHolder.setData(LoadMoreHolder.STATE_LODING_MORE);
        //线程管理
        ThreadManager.getNormalPool().execute(new LoadMoreTask(loadMoreHolder));
    }

    private class LoadMoreTask implements Runnable {

        private final LoadMoreHolder loadMoreHolder;

        public LoadMoreTask(LoadMoreHolder holder) {
            this.loadMoreHolder = holder;
        }

        @Override
        public void run() {
            int state = LoadMoreHolder.STATE_LODING_MORE;   //默认是先加载
            Log.e(TAG, "开始加载更多");

            //1获取数据
            List<T> moreData = null;
            try {
                moreData = getMoreData();
                SystemClock.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();    //在具体的页面报错
                state = LoadMoreHolder.STATE_RETEY;     //显示错误的图片
            }

            //2.添加新的数据,它的类型是list<T>
            if (moreData != null){
                //1,返回的数据不蛮页,小于20条
                if (mData.size() < Constans.PAGE_SIZE){
                    state = LoadMoreHolder.STATE_NONE;
                }
                mData.addAll(moreData);
            }else{
             //没有加载到数据
                //1,所有数据都加载完毕
                state = LoadMoreHolder.STATE_NONE;  //两个都不显示
            }
            Log.e(TAG, "加载更多完成");
            final int finalState = state;   //避免给state设置final
            UiUtil.post(new Runnable() {
                @Override
                public void run() {
                    if (finalState != LoadMoreHolder.STATE_RETEY){
                        //刷新ui
                        notifyDataSetChanged();
                    }
                    isLoadingMore = false;
                    loadMoreHolder.setData(finalState);
                }
            });
        }
    }

    protected abstract List<T> getMoreData() throws Exception;

    protected abstract BaseHolder getBaseHolder();

    //当前页面是否支持加载更多,默认不支持返回false
    public boolean isSupportLoadMore() {
        return false;
    }

    protected  LoadMoreHolder mloadMoreHolder;

    public LoadMoreHolder getLoadMoreHolder() {
        if (mloadMoreHolder == null){
            mloadMoreHolder = new LoadMoreHolder();
        }
        return mloadMoreHolder;
    }
}
