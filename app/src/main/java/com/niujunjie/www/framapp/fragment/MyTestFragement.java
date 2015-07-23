package com.niujunjie.www.framapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.niujunjie.www.framapp.ui.LoadingPage;

/**
 * Created by niujunjie on 2015/7/23.
 */
public class MyTestFragement extends BaseFragment {

    @Override
    protected void initNetData() {
        /**
         * 此处做联网操作成功获取数据后
         */
         showView();
    }

    /**
     * 联网成功之后判断显示什么界面{成功，失败，超时}
     * 此方法在子线程中运行
     * @return
     */
    @Override
    protected LoadingPage.LoadingPageState onLoad() {
        LoadingPage.LoadingPageState pagestate =
                new LoadingPage.LoadingPageState(LoadingPage.LoadingPageState.STATE_SUCCESSED);
        return pagestate;
    }


    @Override
    public View initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView tv = new TextView(getActivity());
        tv.setText("页面加载成功");
        return tv;
    }



}
