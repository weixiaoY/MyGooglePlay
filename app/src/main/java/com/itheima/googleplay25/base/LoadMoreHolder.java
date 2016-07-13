package com.itheima.googleplay25.base;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.googleplay25.R;
import com.itheima.googleplay25.util.UiUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.base
 *  @文件名:   LoadMoreHolder
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/13 19:25
 *  @描述：    TODO
 */
public class LoadMoreHolder
        extends BaseHolder<Integer>
{
    //1,不支持加载更多,所有内容全部加载完
    public static final int STATE_NONE = 0;
    //2,加载更多
    public static final int STATE_LODING_MORE = 2;
    //3.重试
    public static final int STATE_RETEY = 3;

    private static final String TAG = "LoadMoreHolder";
    @Bind(R.id.item_loadmore_container_loading)
    LinearLayout mItemLoadmoreContainerLoading;     //刷新
    @Bind(R.id.item_loadmore_tv_retry)
    TextView     mItemLoadmoreTvRetry;      //重试按钮
    @Bind(R.id.item_loadmore_container_retry)
    LinearLayout mItemLoadmoreContainerRetry;      //刷新失败重试

    @Override
    public View inflateAndFindView() {
        //真数据
        View loadMoreView = View.inflate(UiUtil.getContext(), R.layout.item_load_more, null);
        ButterKnife.bind(this,loadMoreView);

        //简单假数据
       /* TextView tv = new TextView(UiUtil.getContext());
        tv.setText("加载更多数据");
        tv.setTextSize(30);
        tv.setTextColor(Color.RED);
        tv.setGravity(Gravity.CENTER);*/
        return loadMoreView;
    }

    @Override
    public void setData(Integer data) {
        switch (data){
            case STATE_NONE :   //不支持加载更多,就隐藏两个
                mItemLoadmoreContainerLoading.setVisibility(View.GONE);
                mItemLoadmoreContainerRetry.setVisibility(View.GONE);
               break;
            case STATE_LODING_MORE :   //加载更多
                mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
                mItemLoadmoreContainerRetry.setVisibility(View.GONE);
                break;
            case STATE_RETEY :   //加载失败
                mItemLoadmoreContainerLoading.setVisibility(View.GONE);
                mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                break;
        }
    }
}
