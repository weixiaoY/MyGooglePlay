package com.itheima.googleplay25.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.itheima.googleplay25.R;
import com.itheima.googleplay25.manager.ThreadManager;
import com.itheima.googleplay25.util.UiUtil;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.view
 *  @文件名:   LoadDataView
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/8 20:42
 *  @描述：    TODO
 */
public abstract class LoadDataView
        extends FrameLayout
{
    private static final String TAG           = "LoadDataView";
    private static final int    STATE_NONE    = 0;
    private static final int    STATE_EMPTY   = 1;
    private static final int    STATE_LOADING = 2;
    private static final int    STATE_ERROR   = 3;
    private static final int    STATE_SUCCESS = 4;

    private int mCurrentState = STATE_NONE;
    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;
    private View mSuccessView;  //加载成功


    public LoadDataView(Context context) {
        super(context);
        initView();
    }

    public LoadDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mLoadingView = View.inflate(UiUtil.getContext(), R.layout.pager_loading, null);
        addView(mLoadingView);

        mEmptyView = View.inflate(UiUtil.getContext(), R.layout.pager_empty, null);
        addView(mEmptyView);

        mErrorView = View.inflate(UiUtil.getContext(), R.layout.pager_error, null);
        mErrorView.findViewById(R.id.bt_error_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();         //重新加载数据
            }
        });
        addView(mErrorView);
        //添加哪个的到fragment的方法
        updateUI();
    }

    /**
     * 根据服务器返回的结果控制三种状态
     */
    private void updateUI() {
        mLoadingView.setVisibility(mCurrentState == STATE_LOADING
                                   ? VISIBLE
                                   : GONE);
        mEmptyView.setVisibility(mCurrentState == STATE_EMPTY
                                 ? VISIBLE
                                 : GONE);
        mErrorView.setVisibility(mCurrentState == STATE_ERROR
                                 ? VISIBLE
                                 : GONE);

        //有可能状态为mSuccessView
        if (mSuccessView == null && mCurrentState == STATE_SUCCESS) {
            //第一次加载成功
            mSuccessView = onLoadSuccess();
            addView(mSuccessView);
        }

        if (mSuccessView != null) {
            mSuccessView.setVisibility(mCurrentState == STATE_SUCCESS
                                       ? VISIBLE
                                       : GONE);
        }

    }

    //展示成果的view
    protected abstract View onLoadSuccess();


    /**
     *
     */
    public void loadData() {
        if (mCurrentState == STATE_LOADING || mCurrentState == STATE_LOADING){
            return;
        }
        mCurrentState = STATE_LOADING;     //正在加载
        //刷新ui隐藏其他
        updateUI();
        //使用线程池优化3g 4g 同时发出多个http请求
        //1.更好的管理线程开启,回收时机,类似webView每次开启会消耗很多次元,30-100ms开启成功线程需要缓存
        //2.方便排查问题
 //       new Thread(new LoadTast()) {}.start();
        ThreadManager.getNormalPool().execute(new LoadTast());
    }

    public  enum Result {
        SUCCESS(STATE_SUCCESS),
        ERROR(STATE_ERROR),
        EMPTY(STATE_EMPTY);

        Result(int state) {
            //set方法或者构造方法赋值
            this.state = state;
        }

        int state;
        public int getState() {
            return state;
        }
    }

    private class LoadTast
            implements Runnable
    {
        @Override
        public void run() {
            //访问服务器的接口,有些应用中数据来源不只是网络http ,可以从assets sqlite数据库中拿
            //行为都是获取数据,请求方式所有页面不是统一的,不能写在父类中
            Result result = getDataFromServer();
            mCurrentState = result.getState();

            //刷新ui
            //1,activity.runOnUiThead();    //2,handler;
            safeUpdateUi(); //直接在子线程中完成
        }
    }

    private void safeUpdateUi() {
        UiUtil.post(new Runnable() {
            @Override
            public void run() {
                updateUI(); //子线程中的刷新ui
            }
        });
    }

    protected abstract Result getDataFromServer();
}
