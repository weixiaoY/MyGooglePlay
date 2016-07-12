package com.itheima.googleplay25;

import com.itheima.googleplay25.base.LoadDataFragment;
import com.itheima.googleplay25.fragment.AppFragment;
import com.itheima.googleplay25.fragment.CategorFragment;
import com.itheima.googleplay25.fragment.GameFragment;
import com.itheima.googleplay25.fragment.HomeFragment;
import com.itheima.googleplay25.fragment.HotFragment;
import com.itheima.googleplay25.fragment.RecommendFragment;
import com.itheima.googleplay25.fragment.SubjectFragment;

import java.util.HashMap;

/*
 *  @项目名：  MyGoolePlay 
 *  @包名：    com.itheima.googleplay25
 *  @文件名:   FragmentFactory
 *  @创建者:   Administrator
 *  @创建时间:  2016/7/10 19:34
 *  @描述：    fragment工厂
 */
public class FragmentFactory {
    private static final String TAG = "FragmentFactory";

    private static FragmentFactory fragmentFactory;

    private static HashMap<Integer, LoadDataFragment> mCaches = new HashMap<>();

    public static FragmentFactory getFragmentFactory(){
        if (fragmentFactory == null){
            fragmentFactory = new FragmentFactory();
        }
        return fragmentFactory;
    }

    public LoadDataFragment getFragment(int position){
        //如果有缓存,就先取缓存
        LoadDataFragment fragment = null;
        if (mCaches.containsKey(position)){
            fragment = mCaches.get(position);
            return fragment;
        }

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new AppFragment();
                break;
            case 2:
                fragment = new GameFragment();
                break;
            case 3:
                fragment = new SubjectFragment();
                break;
            case 4:
                fragment = new RecommendFragment();
                break;
            case 5:
                fragment = new CategorFragment();
                break;
            case 6:
                fragment = new HotFragment();
                break;
            default:
                break;
        }
        mCaches.put(position,fragment);
        return fragment;
    }
}
