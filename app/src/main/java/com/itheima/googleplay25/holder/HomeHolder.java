package com.itheima.googleplay25.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itheima.googleplay25.R;
import com.itheima.googleplay25.base.BaseHolder;
import com.itheima.googleplay25.beans.HomeBean;
import com.itheima.googleplay25.util.Constans;
import com.itheima.googleplay25.util.UiUtil;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25.base
 *  @文件名:   HomeHolder
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/12 18:50
 *  @描述：    TODO
 */
public class HomeHolder
        extends BaseHolder<HomeBean.ListBean>
{


    private static final String TAG = "HomeHolder";
    @Bind(R.id.item_appinfo_iv_icon)
    ImageView mItemAppinfoIvIcon;
    @Bind(R.id.item_appinfo_tv_title)
    TextView  mItemAppinfoTvTitle;
    @Bind(R.id.item_appinfo_rb_stars)
    RatingBar mItemAppinfoRbStars;
    @Bind(R.id.item_appinfo_tv_size)
    TextView  mItemAppinfoTvSize;
    @Bind(value = R.id.item_appinfo_tv_des) //应用详情文字
    protected TextView mTvDes;

    private View mConvertView;
    //TextView tv;
   /* @Bind(value = R.id.item_appinfo_tv_des);
    protected TextView mDes;*/

    /*@Bind(value = R.id.item_appinfo_tv_des);
    protected TextView mDes;*/



    @Override
    public View inflateAndFindView() {
        mConvertView = View.inflate(UiUtil.getContext(), R.layout.item_app_info, null);
        //tv = (TextView) mConvertView.findViewById(R.id.item_appinfo_tv_des);
        ButterKnife.bind(this, mConvertView);

        return mConvertView;
    }

    /**
     * 设置设置数据
     * @param data
     */
    @Override
    public void setData(HomeBean.ListBean data) {
        mTvDes.setText(data.des);       //应用详情
        mItemAppinfoTvSize.setText(Formatter.formatFileSize(UiUtil.getContext(), data.size));  //应用大小
        mItemAppinfoTvTitle.setText(data.name); //应用名称
        mItemAppinfoRbStars.setRating(data.stars);  //星星评价

        String url =Constans.IMAGE_SERVER + data.iconUrl;
        //System.out.println(url + "HomeHolder+++");
        Picasso.with(UiUtil.getContext()).load(url).noFade()
               .error(R.drawable.ic_default)
               .placeholder(R.drawable.ic_default)
               .into(mItemAppinfoIvIcon);
    }
}
