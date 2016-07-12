package com.itheima.googleplay25.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.googleplay25.FragmentFactory;
import com.itheima.googleplay25.R;
import com.itheima.googleplay25.base.BaseActivity;
import com.itheima.googleplay25.base.LoadDataFragment;
import com.itheima.googleplay25.util.UiUtil;

import org.itheima.tabindicator.library.TabIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity
        extends BaseActivity
        implements ViewPager.OnPageChangeListener
{

    @Bind(R.id.main_draw_left)
    FrameLayout  mMainDrawLeft;
    @Bind(R.id.main_draw_content)
    LinearLayout mMainDrawContent;
    @Bind(R.id.main_draw)
    DrawerLayout mMainDraw;
    @Bind(R.id.vp_title)
    ViewPager    mVpTitle;

    @Bind(R.id.indicator)
    TabIndicator mTabIndicator;


    private ActionBar             mActionBar;
    private ActionBarDrawerToggle mToggle;
    private String[] mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initData() {
        //获取titile
        mTitle = UiUtil.getStringArray(R.array.pagers);
        mVpTitle.setAdapter(new MainFragmentAdapter(getSupportFragmentManager()));

        mTabIndicator.setViewPager(mVpTitle);

        //可选 懒加载   监听器
        mTabIndicator.addOnPageChangeListener(this);
    }

    @Override   //当页面滚动时
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override   //当页面选择时
    public void onPageSelected(int position) {
        //当页面选择是懒加载
       //1,调用adapter的get方法获取fragment的对象,adapter做成成员变量即可
        LoadDataFragment  fragment = FragmentFactory.getFragmentFactory().getFragment(position);
        fragment.loadData();
        //2,保存getItem中的fragment,然后再取出
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTabIndicator != null){
            mTabIndicator.removeOnPageChangeListener(this);
        }
    }

    private class MainFragmentAdapter extends FragmentPagerAdapter{
        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Log.d("MainFragmentAdapter", ">>>>>>" + position);
            Fragment fragment = FragmentFactory.getFragmentFactory().getFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            if (mTitle != null){
                return mTitle.length;
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mTitle != null){
                return mTitle[position];
            }
            return super.getPageTitle(position);
        }
    }

    /*private class MainFragmentAdapter extends FragmentPagerAdapter{

        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Log.d("MainFragmentAdapter", ">>>>>>" + position);
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                     break;
                case 1:
                    fragment = new AppFragment();
                    break;

                default:
                    fragment = new HomeFragment();
                     break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            if (mTitle != null){
                return mTitle.length;
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mTitle != null){
                return mTitle[position];
            }
            return super.getPageTitle(position);
        }
    }*/

    private class MainPagerAdapter extends PagerAdapter{

        @Override
        public CharSequence getPageTitle(int position) {
            if (mVpTitle != null){
                return mTitle[position];
            }
            return super.getPageTitle(position);
        }

        //添加
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView tv = new TextView(UiUtil.getContext());
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(25);
            tv.setTextColor(Color.BLACK);
            tv.setText(mTitle[position]);
            container.addView(tv);
            return tv;
        }

        //划过之后删除
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            if (mTitle != null){
                return mTitle.length;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }



    private void initView() {
        //添加actionbar
        mActionBar = getSupportActionBar();
        //加上返回按钮
        mActionBar.setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        mToggle = new ActionBarDrawerToggle(this,
                                            mMainDraw,
                                            R.string.open_drawer,
                                            R.string.close_drawer);
        //同步的状态
        mToggle.syncState();
        //监听器
        mMainDraw.addDrawerListener(mToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mToggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
