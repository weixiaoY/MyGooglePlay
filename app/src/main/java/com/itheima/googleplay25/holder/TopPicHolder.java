package com.itheima.googleplay25.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.googleplay25.R;
import com.itheima.googleplay25.base.BaseHolder;
import com.itheima.googleplay25.util.Constans;
import com.itheima.googleplay25.util.UiUtil;
import com.itheima.googleplay25.view.LoopViewPager;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.holder
 *  @文件名:   TopPicHolder
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/15 10:22
 *  @描述：    新闻轮播图的实现.利用类似网易新闻的轮播图,LoopViewPager
 */
public class TopPicHolder
        extends BaseHolder<List<String>> implements View.OnTouchListener
{
    private static final String TAG = "TopPicHolder";
    @Bind(R.id.item_home_picture_pager)
    LoopViewPager mItemHomePicturePager;
    @Bind(R.id.title)
    TextView      mTitle;       //新闻轮播图的标题
    @Bind(R.id.dot_container)
    LinearLayout  mDotContainer;    //新闻轮播图的下标


    private List<String> mDatas;

    private int mLastSelected = 0;  //标记上次选中点的位置
    private int mDotSize = 8;
    private AutoScrollTask mAutoScrollTask;

    public View inflateAndFindView() {
        View view = View.inflate(UiUtil.getContext(), R.layout.item_home_picture, null);
        ButterKnife.bind(this, view);
        return view;
    }

    public void setData(List<String> data) {
        this.mDatas = data;
        initDataList();
        mItemHomePicturePager.setAdapter(new HomePicAdapter());
        mItemHomePicturePager.setOnPageChangeListener(mOnChangeListener);

       // int currentItem = mItemHomePicturePager.getCurrentItem();
        //自动播放下一个图片
        mItemHomePicturePager.setOnTouchListener(this);

        mAutoScrollTask = new AutoScrollTask();
        mAutoScrollTask.start();
    }

    public boolean onTouch(View v, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:   // 新闻轮播图点下的时候停止轮播任务
                mAutoScrollTask.stop();     //停止任务
                break;
            case MotionEvent.ACTION_UP:   // 新闻弹起触摸
            case MotionEvent.ACTION_CANCEL: //滑动一般取消触摸
                mAutoScrollTask.start();
                break;

        }
        return false;
    }

    private class AutoScrollTask implements Runnable{

        @Override
        public void run() {
            //切换到下一张图片
            int current = mItemHomePicturePager.getCurrentItem();
            current += 1;
            mItemHomePicturePager.setCurrentItem(current);
            UiUtil.postDelayed(this,4000);
        }
        public void start(){
            stop();
            UiUtil.postDelayed(this,4000);
        }
        private void stop() {UiUtil.removeCallbacks(this);}
    }

    private void initDataList() {
        mDotContainer.removeAllViews();
        //mDataList = new ArrayList<ImageView>();
        for (int i = 0; i < mDatas.size(); i++) {
			/*ImageView page = new ImageView(getContext());
			page.setImageResource(mImages[i]);
			page.setScaleType(ScaleType.FIT_XY);
			mDataList.add(page);*/
            //初始化点
            //创建点
            View dot = new View(UiUtil.getContext());
            //设置点的布局参数
            //			int dotSize = getResources().getDimensionPixelSize(R.dimen.dot_size);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mDotSize,
                                                                                   mDotSize);
            //最后一个点不要margin,让点居中
            if (i != 9) {
                layoutParams.rightMargin = 8;
            }

            dot.setLayoutParams(layoutParams);
            //第一个点默认选中
            if (i == 0) {
                dot.setBackgroundResource(R.drawable.dot_selected);
            } else {
                dot.setBackgroundResource(R.drawable.dot_normal);
            }
            //将点添加到点的容器
            mDotContainer.addView(dot);
        }
    }




    OnPageChangeListener mOnChangeListener = new OnPageChangeListener() {

        @Override   //当页面发生滚动的回调
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.e(TAG, "新闻轮播图下标" + position);
        }

        @Override       // 当页面被选中的回调
        public void onPageSelected(int position) {
            if (position == mLastSelected) {
                return;
            }

            position = position % mDatas.size();
            //切换标题
            //mTitle.setText(mTitles[position]);

            mTitle.setText("新闻标题");
            //切换点
            View dot = mDotContainer.getChildAt(position);
            dot.setBackgroundResource(R.drawable.dot_selected);

            //将上次选中点恢复成白色
            View preDot = mDotContainer.getChildAt(mLastSelected);
            preDot.setBackgroundResource(R.drawable.dot_normal);

            //更新上次选中点
            mLastSelected = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


     class HomePicAdapter
            extends PagerAdapter
    {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView iv  = new ImageView(UiUtil.getContext());
            String    url = Constans.IMAGE_SERVER + mDatas.get(position);
            //设置新闻轮播图的图片url
            Picasso.with(UiUtil.getContext())
                   .load(url)
                   .into(iv);
            container.addView(iv);


            //设置图片的缩放
            iv.setScaleType(ImageView.ScaleType.FIT_XY);    //填充满屏幕


            //新闻轮播图的点击事件
            iv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                    intent.putExtra(Contant.EXRAT_NEWS_DETAIL, mTopNews.get(position).url);
                    intent.putExtra(Contant.EXRAT_NEWS_TITLE, mTopNews.get(position).title);
                    getContext().startActivity(intent);*/
                }
            });
            //返回页面标记 =页面本身
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            ;
        }

        @Override
        public int getCount() {
            if (mDatas == null) {
                return 0;
            }
            return mDatas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


    }
}
