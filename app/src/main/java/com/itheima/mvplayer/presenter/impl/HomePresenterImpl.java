package com.itheima.mvplayer.presenter.impl;

import com.itheima.mvplayer.model.HomeItemBean;
import com.itheima.mvplayer.model.NetworkCallback;
import com.itheima.mvplayer.model.NetworkManager;
import com.itheima.mvplayer.presenter.HomePresenter;
import com.itheima.mvplayer.view.HomeView;

import java.util.ArrayList;
import java.util.List;


public class HomePresenterImpl implements HomePresenter {
    public static final String TAG = "HomePresenterImpl";

    private HomeView mHomeView;

    private List<HomeItemBean> mHomeItemBeanList;

    public HomePresenterImpl(HomeView homeView) {
        mHomeView = homeView;
        mHomeItemBeanList = new ArrayList<HomeItemBean>();
    }

    @Override
    public void loadHomeData() {
        NetworkManager.getInstance().loadHomeData(new NetworkCallback<List<HomeItemBean>>() {
            @Override
            public void onError() {
                mHomeView.onLoadHomeDataFailed();
            }

            @Override
            public void onSuccess(List<HomeItemBean> result) {
                mHomeItemBeanList.addAll(result);
                mHomeView.onLoadHomeDataSuccess();
            }
        });
    }

    @Override
    public List<HomeItemBean> getHomeListItems() {
        return mHomeItemBeanList;
    }

}