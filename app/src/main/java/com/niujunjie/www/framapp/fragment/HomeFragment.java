package com.niujunjie.www.framapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.niujunjie.www.framapp.R;
import com.niujunjie.www.framapp.ui.Image3DSwitchView;
import com.niujunjie.www.framapp.ui.LoadingPage;
import com.niujunjie.www.framapp.ui.XListView;

/**
 * Created by niujunjie on 2015/7/23.
 */
public class HomeFragment extends BaseFragment {


    private XListView lv_iamge;


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
    protected void requestNetData() {
        showView();
    }


    @Override
    public View initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view  = inflater.inflate(R.layout.fragment_home,null);
        lv_iamge = (XListView) view.findViewById(R.id.lv_image);
        preparationWork();
        return view;
    }

    /**
     * 数据准备工作
     */
    private void preparationWork() {
       lv_iamge.setPullLoadEnable(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
